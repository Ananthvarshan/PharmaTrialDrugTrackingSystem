package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Role;
import connection.DatabaseConnection;

public class RoleDAO {

    // --- Add a new role ---
    public boolean addRole(Role role) {
        String sql = "INSERT INTO role (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role.getName());
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error adding role: " + e.getMessage());
            return false;
        }
    }

    // --- Get all roles ---
    public ArrayList<Role> getAllRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM role";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching roles: " + e.getMessage());
        }

        return roles;
    }

    // --- Get role by ID ---
    public Role getRoleById(int id) {
        String sql = "SELECT * FROM role WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));
                    return role;
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error fetching role by ID: " + e.getMessage());
        }

        return null;
    }

    // --- Update role ---
    public boolean updateRole(Role role) {
        String sql = "UPDATE role SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role.getName());
            pstmt.setInt(2, role.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error updating role: " + e.getMessage());
            return false;
        }
    }

    // --- Delete role ---
    public boolean deleteRole(int id) {
        String sql = "DELETE FROM role WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting role: " + e.getMessage());
            return false;
        }
    }
}
