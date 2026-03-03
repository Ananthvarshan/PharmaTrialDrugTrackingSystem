package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ClinicalTrial;
import connection.DatabaseConnection;

public class ClinicalTrialDAO {

    // --- Add a new clinical trial ---
    public boolean addClinicalTrial(ClinicalTrial trial) {
        String sql = "INSERT INTO clinical_trial (title, description, start_date, end_date, site_id, current_phase, phase_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trial.getTitle());
            pstmt.setString(2, trial.getDescription());
            pstmt.setDate(3, trial.getStartDate());
            pstmt.setDate(4, trial.getEndDate());
            pstmt.setInt(5, trial.getSiteId());
            pstmt.setString(6, trial.getCurrentPhase());
            pstmt.setString(7, trial.getPhaseStatus());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error adding clinical trial: " + e.getMessage());
            return false;
        }
    }

    // --- Get all clinical trials ---
    public ArrayList<ClinicalTrial> getAllClinicalTrials() {
        ArrayList<ClinicalTrial> trials = new ArrayList<>();
        String sql = "SELECT * FROM clinical_trial";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ClinicalTrial trial = mapResultSetToTrial(rs);
                trials.add(trial);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching clinical trials: " + e.getMessage());
        }

        return trials;
    }

    // --- Get clinical trial by ID ---
    public ClinicalTrial getClinicalTrialById(int id) {
        String sql = "SELECT * FROM clinical_trial WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTrial(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching clinical trial by ID: " + e.getMessage());
        }

        return null;
    }

    // --- Update clinical trial ---
    public boolean updateClinicalTrial(ClinicalTrial trial) {
        String sql = "UPDATE clinical_trial SET title = ?, description = ?, start_date = ?, end_date = ?, site_id = ?, " +
                     "current_phase = ?, phase_status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, trial.getTitle());
            pstmt.setString(2, trial.getDescription());
            pstmt.setDate(3, trial.getStartDate());
            pstmt.setDate(4, trial.getEndDate());
            pstmt.setInt(5, trial.getSiteId());
            pstmt.setString(6, trial.getCurrentPhase());
            pstmt.setString(7, trial.getPhaseStatus());
            pstmt.setInt(8, trial.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error updating clinical trial: " + e.getMessage());
            return false;
        }
    }

    // --- Delete clinical trial by ID ---
    public boolean deleteClinicalTrial(int id) {
        String sql = "DELETE FROM clinical_trial WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting clinical trial: " + e.getMessage());
            return false;
        }
    }

    // --- Get trials by clinical site ---
    public ArrayList<ClinicalTrial> getTrialsBySite(int siteId) {
        ArrayList<ClinicalTrial> trials = new ArrayList<>();
        String sql = "SELECT * FROM clinical_trial WHERE site_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, siteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ClinicalTrial trial = mapResultSetToTrial(rs);
                    trials.add(trial);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching trials by site: " + e.getMessage());
        }

        return trials;
    }

    // --- Helper: Map ResultSet row to ClinicalTrial object ---
    private ClinicalTrial mapResultSetToTrial(ResultSet rs) throws SQLException {
        ClinicalTrial trial = new ClinicalTrial();
        trial.setId(rs.getInt("id"));
        trial.setTitle(rs.getString("title"));
        trial.setDescription(rs.getString("description"));
        trial.setStartDate(rs.getDate("start_date"));
        trial.setEndDate(rs.getDate("end_date"));
        trial.setSiteId(rs.getInt("site_id"));
        trial.setCurrentPhase(rs.getString("current_phase"));
        trial.setPhaseStatus(rs.getString("phase_status"));
        return trial;
    }
}
