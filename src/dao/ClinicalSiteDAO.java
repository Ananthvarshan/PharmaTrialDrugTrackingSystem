package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ClinicalSite;
import connection.DatabaseConnection;

public class ClinicalSiteDAO {

    // --- Add a new clinical site ---
    public boolean addClinicalSite(ClinicalSite site) {
        String sql = "INSERT INTO clinical_site (name, location, image_url, max_doctors, max_patients, " +
                     "current_doctors, current_patients, total_workers) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, site.getName());
            pstmt.setString(2, site.getLocation());
            pstmt.setString(3, site.getImageUrl());
            pstmt.setInt(4, site.getMaxDoctors());
            pstmt.setInt(5, site.getMaxPatients());
            pstmt.setInt(6, site.getCurrentDoctors());
            pstmt.setInt(7, site.getCurrentPatients());
            pstmt.setInt(8, site.getTotalWorkers());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error adding clinical site: " + e.getMessage());
            return false;
        }
    }

    // --- Get all clinical sites ---
    public ArrayList<ClinicalSite> getAllSites() {
        ArrayList<ClinicalSite> sites = new ArrayList<>();
        String sql = "SELECT * FROM clinical_site";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ClinicalSite site = mapResultSetToSite(rs);
                sites.add(site);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching clinical sites: " + e.getMessage());
        }

        return sites;
    }

    // --- Get clinical site by ID ---
    public ClinicalSite getSiteById(int id) {
        String sql = "SELECT * FROM clinical_site WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSite(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching clinical site by ID: " + e.getMessage());
        }

        return null;
    }

    // --- Update clinical site ---
    public boolean updateClinicalSite(ClinicalSite site) {
        String sql = "UPDATE clinical_site SET name = ?, location = ?, image_url = ?, max_doctors = ?, " +
                     "max_patients = ?, current_doctors = ?, current_patients = ?, total_workers = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, site.getName());
            pstmt.setString(2, site.getLocation());
            pstmt.setString(3, site.getImageUrl());
            pstmt.setInt(4, site.getMaxDoctors());
            pstmt.setInt(5, site.getMaxPatients());
            pstmt.setInt(6, site.getCurrentDoctors());
            pstmt.setInt(7, site.getCurrentPatients());
            pstmt.setInt(8, site.getTotalWorkers());
            pstmt.setInt(9, site.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error updating clinical site: " + e.getMessage());
            return false;
        }
    }

    // --- Delete clinical site by ID ---
    public boolean deleteClinicalSite(int id) {
        String sql = "DELETE FROM clinical_site WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting clinical site: " + e.getMessage());
            return false;
        }
    }

    // --- Optional: Find site by name ---
    public ClinicalSite getSiteByName(String name) {
        String sql = "SELECT * FROM clinical_site WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSite(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching clinical site by name: " + e.getMessage());
        }

        return null;
    }

    // --- Helper: Map ResultSet row to ClinicalSite object ---
    private ClinicalSite mapResultSetToSite(ResultSet rs) throws SQLException {
        ClinicalSite site = new ClinicalSite();
        site.setId(rs.getInt("id"));
        site.setName(rs.getString("name"));
        site.setLocation(rs.getString("location"));
        site.setImageUrl(rs.getString("image_url"));
        site.setMaxDoctors(rs.getInt("max_doctors"));
        site.setMaxPatients(rs.getInt("max_patients"));
        site.setCurrentDoctors(rs.getInt("current_doctors"));
        site.setCurrentPatients(rs.getInt("current_patients"));
        site.setTotalWorkers(rs.getInt("total_workers"));
        return site;
    }
}
