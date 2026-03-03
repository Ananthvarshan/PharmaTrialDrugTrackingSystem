package setup;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import connection.DatabaseConnection; // <- matches your new package

public class CreateTables {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();

            // 1. role
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS role (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(50) UNIQUE NOT NULL
                );
            """);

            // 2. clinical_site
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS clinical_site (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) UNIQUE NOT NULL,
                    location VARCHAR(150),
                    image_url VARCHAR(255),
                    max_doctors INT DEFAULT 0,
                    max_patients INT DEFAULT 0,
                    current_doctors INT DEFAULT 0,
                    current_patients INT DEFAULT 0,
                    total_workers INT DEFAULT 0
                );
            """);

            // 3. user_account
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS user_account (
                    id SERIAL PRIMARY KEY,
                    username VARCHAR(100) UNIQUE NOT NULL,
                    full_name VARCHAR(150),
                    email VARCHAR(150) UNIQUE NOT NULL,
                    password VARCHAR(150) NOT NULL,
                    role_id INT REFERENCES role(id) ON DELETE SET NULL,
                    clinical_site_id INT REFERENCES clinical_site(id) ON DELETE SET NULL,
                    hospital_name VARCHAR(150),
                    created_by INT REFERENCES user_account(id) ON DELETE SET NULL,
                    status VARCHAR(20) DEFAULT 'Active',
                    can_register BOOLEAN DEFAULT FALSE
                );
            """);

            // 4. clinical_trial
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS clinical_trial (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(200) NOT NULL,
                    description TEXT,
                    start_date DATE,
                    end_date DATE,
                    site_id INT REFERENCES clinical_site(id) ON DELETE CASCADE,
                    current_phase VARCHAR(50),
                    phase_status VARCHAR(50) DEFAULT 'Ongoing'
                );
            """);

            // 5. trial_phase
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS trial_phase (
                    id SERIAL PRIMARY KEY,
                    trial_id INT REFERENCES clinical_trial(id) ON DELETE CASCADE,
                    phase_name VARCHAR(50),
                    start_date DATE,
                    end_date DATE,
                    summary TEXT,
                    doctor_count INT DEFAULT 0,
                    patient_count INT DEFAULT 0
                );
            """);

            // 6. trial_doctor
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS trial_doctor (
                    id SERIAL PRIMARY KEY,
                    doctor_id INT REFERENCES user_account(id) ON DELETE CASCADE,
                    trial_id INT REFERENCES clinical_trial(id) ON DELETE CASCADE
                );
            """);

            // 7. trial_participant
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS trial_participant (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(150) NOT NULL,
                    dob DATE,
                    gender VARCHAR(10),
                    enrolled_date DATE DEFAULT CURRENT_DATE,
                    trial_id INT REFERENCES clinical_trial(id) ON DELETE CASCADE,
                    site_id INT REFERENCES clinical_site(id) ON DELETE CASCADE,
                    created_by INT REFERENCES user_account(id) ON DELETE SET NULL
                );
            """);

            // 8. consent_form
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS consent_form (
                    id SERIAL PRIMARY KEY,
                    participant_id INT REFERENCES trial_participant(id) ON DELETE CASCADE,
                    signed_date DATE DEFAULT CURRENT_DATE,
                    document_path VARCHAR(255)
                );
            """);

            // 9. medication
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS medication (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    manufacturer VARCHAR(100)
                );
            """);

            // 10. trial_medication
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS trial_medication (
                    id SERIAL PRIMARY KEY,
                    trial_id INT REFERENCES clinical_trial(id) ON DELETE CASCADE,
                    medication_id INT REFERENCES medication(id) ON DELETE CASCADE,
                    dosage VARCHAR(100)
                );
            """);

            // 11. appointment
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS appointment (
                    id SERIAL PRIMARY KEY,
                    participant_id INT REFERENCES trial_participant(id) ON DELETE CASCADE,
                    doctor_id INT REFERENCES user_account(id) ON DELETE CASCADE,
                    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    purpose VARCHAR(200),
                    appointment_type VARCHAR(20) DEFAULT 'Scheduled',
                    status VARCHAR(20) DEFAULT 'Pending'
                );
            """);

            // 12. patient_report
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS patient_report (
                    id SERIAL PRIMARY KEY,
                    participant_id INT REFERENCES trial_participant(id) ON DELETE CASCADE,
                    report_date DATE DEFAULT CURRENT_DATE,
                    notes TEXT,
                    drowsiness_percent NUMERIC(5,2),
                    fatigue_percent NUMERIC(5,2),
                    nausea_percent NUMERIC(5,2),
                    site_admin_id INT REFERENCES user_account(id) ON DELETE SET NULL
                );
            """);

            // 13. cro_report
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS cro_report (
                    id SERIAL PRIMARY KEY,
                    trial_id INT REFERENCES clinical_trial(id) ON DELETE CASCADE,
                    report_date DATE DEFAULT CURRENT_DATE,
                    overall_summary TEXT,
                    drowsy_rate NUMERIC(5,2),
                    fatigue_rate NUMERIC(5,2),
                    nausea_rate NUMERIC(5,2)
                );
            """);

            // 14. regulatory_authority
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS regulatory_authority (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(150) NOT NULL,
                    contact_email VARCHAR(150),
                    phone VARCHAR(50),
                    address TEXT
                );
            """);

            // 15. approval_request
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS approval_request (
                    id SERIAL PRIMARY KEY,
                    cro_report_id INT REFERENCES cro_report(id) ON DELETE CASCADE,
                    authority_id INT REFERENCES regulatory_authority(id) ON DELETE CASCADE,
                    request_date DATE DEFAULT CURRENT_DATE,
                    status VARCHAR(50) DEFAULT 'Pending',
                    remarks TEXT
                );
            """);

            // 16. audit_log
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS audit_log (
                    id SERIAL PRIMARY KEY,
                    user_id INT REFERENCES user_account(id) ON DELETE SET NULL,
                    action VARCHAR(255),
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
            """);

            // --- Trigger function for updating doctor count ---
            stmt.executeUpdate("""
                CREATE OR REPLACE FUNCTION update_doctor_count()
                RETURNS TRIGGER AS $$
                DECLARE
                    doctor_role_id INT;
                    current_count INT;
                    max_allowed INT;
                BEGIN
                    SELECT id INTO doctor_role_id FROM role WHERE name = 'Doctor' LIMIT 1;

                    IF TG_OP = 'INSERT' AND NEW.role_id = doctor_role_id THEN
                        SELECT max_doctors, total_workers INTO max_allowed, current_count
                        FROM clinical_site WHERE id = NEW.clinical_site_id;

                        IF current_count >= max_allowed THEN
                            RAISE EXCEPTION 'Doctor limit exceeded for this clinical site';
                        END IF;

                        UPDATE clinical_site
                        SET total_workers = total_workers + 1
                        WHERE id = NEW.clinical_site_id;
                    ELSIF TG_OP = 'DELETE' AND OLD.role_id = doctor_role_id THEN
                        UPDATE clinical_site
                        SET total_workers = GREATEST(total_workers - 1, 0)
                        WHERE id = OLD.clinical_site_id;
                    END IF;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
            """);

            // --- Trigger function for updating patient count ---
            stmt.executeUpdate("""
                CREATE OR REPLACE FUNCTION update_patient_count()
                RETURNS TRIGGER AS $$
                DECLARE
                    patient_role_id INT;
                    current_count INT;
                    max_allowed INT;
                BEGIN
                    SELECT id INTO patient_role_id FROM role WHERE name = 'Patient' LIMIT 1;

                    IF TG_OP = 'INSERT' AND NEW.role_id = patient_role_id THEN
                        SELECT max_patients, total_workers INTO max_allowed, current_count
                        FROM clinical_site WHERE id = NEW.clinical_site_id;

                        IF current_count >= max_allowed THEN
                            RAISE EXCEPTION 'Patient limit exceeded for this clinical site';
                        END IF;

                        UPDATE clinical_site
                        SET total_patients = total_patients + 1
                        WHERE id = NEW.clinical_site_id;
                    ELSIF TG_OP = 'DELETE' AND OLD.role_id = patient_role_id THEN
                        UPDATE clinical_site
                        SET total_patients = GREATEST(total_patients - 1, 0)
                        WHERE id = OLD.clinical_site_id;
                    END IF;
                    RETURN NEW;
                END;
                $$ LANGUAGE plpgsql;
            """);

            // --- Attach triggers to user_account ---
            stmt.executeUpdate("""
                CREATE OR REPLACE TRIGGER trg_update_doctor_count
                AFTER INSERT OR DELETE ON user_account
                FOR EACH ROW
                EXECUTE FUNCTION update_doctor_count();
            """);

            stmt.executeUpdate("""
                CREATE OR REPLACE TRIGGER trg_update_patient_count
                AFTER INSERT OR DELETE ON user_account
                FOR EACH ROW
                EXECUTE FUNCTION update_patient_count();
            """);

            System.out.println("✅ All 16 tables created successfully!");

        } catch (SQLException e) {
            System.out.println("❌ Error creating tables: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
