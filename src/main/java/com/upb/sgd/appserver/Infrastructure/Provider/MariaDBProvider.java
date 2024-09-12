package com.upb.sgd.appserver.Infrastructure.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.upb.sgd.appserver.Domain.Port.UserDBProvider;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

public class MariaDBProvider implements UserDBProvider {

    private final Connection connection;

    public MariaDBProvider(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User GetUser(int id) {
        String queryUser = "SELECT * FROM USER WHERE Id = ?";
        String queryGroups = "SELECT GroupId FROM USERGROUP WHERE UserId = ?";

        try (PreparedStatement stmtUser = connection.prepareStatement(queryUser); PreparedStatement stmtGroups = connection.prepareStatement(queryGroups)) {
            stmtUser.setInt(1, id);
            ResultSet rsUser = stmtUser.executeQuery();
            if (rsUser.next()) {
                User u = new User();
                u.Id = rsUser.getInt("Id");
                u.username = rsUser.getString("username");
                u.password = rsUser.getString("password");

                stmtGroups.setInt(1, id);
                ResultSet rsGroups = stmtGroups.executeQuery();
                List<Integer> groupIds = new ArrayList<>();
                while (rsGroups.next()) {
                    groupIds.add(rsGroups.getInt("GroupId"));
                }
                u.groupPermIds = groupIds;

                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> GetUsers() {
        String queryUser = "SELECT * FROM USER";
        String queryGroups = "SELECT GroupId FROM USERGROUP WHERE UserId = ?";
        List<User> users = new ArrayList<>();

        try (PreparedStatement stmtUser = connection.prepareStatement(queryUser); PreparedStatement stmtGroups = connection.prepareStatement(queryGroups)) {

            ResultSet rsUser = stmtUser.executeQuery();
            while (rsUser.next()) {
                User u = new User();
                u.Id = rsUser.getInt("Id");
                u.username = rsUser.getString("username");
                u.password = rsUser.getString("password");

                stmtGroups.setInt(1, u.Id);
                ResultSet rsGroups = stmtGroups.executeQuery();
                List<Integer> groupIds = new ArrayList<>();
                while (rsGroups.next()) {
                    groupIds.add(rsGroups.getInt("GroupId"));
                }
                u.groupPermIds = groupIds;

                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error getting users: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User Login(String username, String password) {
        String queryUser = "SELECT * FROM USER WHERE username = ? AND password = ?";
        String queryGroups = "SELECT GroupId FROM USERGROUP WHERE UserId = ?";

        try (PreparedStatement stmtUser = connection.prepareStatement(queryUser); PreparedStatement stmtGroups = connection.prepareStatement(queryGroups)) {

            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            ResultSet rsUser = stmtUser.executeQuery();
            if (rsUser.next()) {
                User u = new User();
                u.Id = rsUser.getInt("Id");
                u.username = rsUser.getString("username");
                u.password = rsUser.getString("password");
                u.isAdmin = rsUser.getInt("isAdmin") == 1;
                stmtGroups.setInt(1, u.Id);
                ResultSet rsGroups = stmtGroups.executeQuery();
                List<Integer> groupIds = new ArrayList<>();
                while (rsGroups.next()) {
                    groupIds.add(rsGroups.getInt("GroupId"));
                }
                u.groupPermIds = groupIds;

                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error login in: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean CreateUser(int groupPermId, String username, String password) {
        String query = "INSERT INTO USER (username, password, groupPermId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, groupPermId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean DeleteUser(int id) {
        String query = "DELETE FROM USER WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean UpdateUser(int id, List<Integer> groupPermIds, String username, String password) {
        String queryUser = "UPDATE USER SET username = ?, password = ? WHERE Id = ?";
        String deleteUserGroups = "DELETE FROM USERGROUP WHERE UserId = ?";
        String insertUserGroups = "INSERT INTO USERGROUP (UserId, GroupId) VALUES (?, ?)";

        try (PreparedStatement stmtUser = connection.prepareStatement(queryUser); PreparedStatement stmtDeleteGroups = connection.prepareStatement(deleteUserGroups); PreparedStatement stmtInsertGroups = connection.prepareStatement(insertUserGroups)) {
            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            stmtUser.setInt(3, id);
            stmtUser.executeUpdate();
            stmtDeleteGroups.setInt(1, id);
            stmtDeleteGroups.executeUpdate();

            for (Integer groupId : groupPermIds) {
                stmtInsertGroups.setInt(1, id);
                stmtInsertGroups.setInt(2, groupId);
                stmtInsertGroups.addBatch();
            }
            stmtInsertGroups.executeBatch();

            return true;
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Group GetGroup(int id) {
        String query = "SELECT * FROM `GROUP` WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Group g = new Group();
                g.Id = rs.getInt("Id");
                g.Name = rs.getString("Name");
                return g;
            }
        } catch (SQLException e) {
            System.out.println("Error getting group: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Group> GetGroups() {
        String query = "SELECT * FROM `GROUP`";
        List<Group> groups = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Group g = new Group();
                g.Id = rs.getInt("Id");
                g.Name = rs.getString("Name");
                groups.add(g);
            }
        } catch (SQLException e) {
            System.out.println("Error gettin groups: " + e.getMessage());
        }
        return groups;
    }

    @Override
    public Boolean CreateGroup(String name) {
        String query = "INSERT INTO `GROUP` (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error creating group: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean DeleteGroup(int id) {
        String query = "DELETE FROM `GROUP` WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting group: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean UpdateGroup(int id, String name) {
        String query = "UPDATE `GROUP` SET Name = ? WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating group: " + e.getMessage());
        }
        return false;
    }
}
