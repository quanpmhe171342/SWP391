package DAO;

import Model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    private Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new role
    public boolean addRole(Role role) {
        String sql = "INSERT INTO roles (roleName) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getRoleName());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get a role by ID
    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM roles WHERE roleId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String roleName = rs.getString("roleName");
                return new Role(roleId, roleName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all roles
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int roleId = rs.getInt("roleId");
                String roleName = rs.getString("roleName");
                roles.add(new Role(roleId, roleName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Method to update a role
    public boolean updateRole(Role role) {
        String sql = "UPDATE roles SET roleName = ? WHERE roleId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getRoleName());
            stmt.setInt(2, role.getRoleId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a role
    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM roles WHERE roleId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
