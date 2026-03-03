package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.UserAccount;
import connection.DatabaseConnection;

public class UserAccountDAO {

    // --- Add a new user ---
    public boolean addUser(UserAccount user) {
        String sql = "INSERT INTO user_account (username, full_name, email, password, role_id, clinical_site_id, " +
                     "hospital_name, created_by, status, can_register) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getRoleId());
            pstmt.setInt(6, user.getClinicalSiteId());
            pstmt.setString(7, user.getHospitalName());
            pstmt.setInt(8, user.getCreatedBy());
            pstmt.setString(9, user.getStatus());
            pstmt.setBoolean(10, user.isCanRegister());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error adding user: " + e.getMessage());
            return false;
        }
    }

    // --- Get all users ---
    public ArrayList<UserAccount> getAllUsers() {
        ArrayList<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM user_account";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching users: " + e.getMessage());
        }

        return users;
    }

    // --- Get user by ID ---
    public UserAccount getUserById(int id) {
        String sql = "SELECT * FROM user_account WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching user by ID: " + e.getMessage());
        }

        return null;
    }

    // --- Update user ---
    public boolean updateUser(UserAccount user) {
        String sql = "UPDATE user_account SET username = ?, full_name = ?, email = ?, password = ?, " +
                     "role_id = ?, clinical_site_id = ?, hospital_name = ?, created_by = ?, status = ?, can_register = ? " +
                     "WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getRoleId());
            pstmt.setInt(6, user.getClinicalSiteId());
            pstmt.setString(7, user.getHospitalName());
            pstmt.setInt(8, user.getCreatedBy());
            pstmt.setString(9, user.getStatus());
            pstmt.setBoolean(10, user.isCanRegister());
            pstmt.setInt(11, user.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
            return false;
        }
    }

    // --- Delete user by ID ---
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user_account WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // --- Optional: Get users by role ---
    public ArrayList<UserAccount> getUsersByRole(int roleId) {
        ArrayList<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM user_account WHERE role_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching users by role: " + e.getMessage());
        }

        return users;
    }

    // --- Helper: Map ResultSet row to UserAccount object ---
    private UserAccount mapResultSetToUser(ResultSet rs) throws SQLException {
        UserAccount user = new UserAccount();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRoleId(rs.getInt("role_id"));
        user.setClinicalSiteId(rs.getInt("clinical_site_id"));
        user.setHospitalName(rs.getString("hospital_name"));
        user.setCreatedBy(rs.getInt("created_by"));
        user.setStatus(rs.getString("status"));
        user.setCanRegister(rs.getBoolean("can_register"));
        return user;
    }
}
