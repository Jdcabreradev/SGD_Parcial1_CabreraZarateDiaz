package com.upb.sgd.dataserver.database.infrastructure.mariadb;

import com.upb.sgd.dataserver.database.domain.port.driven.DatabaseServicePort;
import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MariaDBService implements DatabaseServicePort {

    private final Connection connection;

    public MariaDBService(Connection connection) {
        this.connection = connection;
    }

    public List<Directory> findDirByParent(Integer parentId) {
        List<Directory> directories = new ArrayList<>();
        String query;
        if (parentId == null) {
            query = "SELECT * FROM Directory WHERE parent IS NULL";
        } else {
            query = "SELECT * FROM Directory WHERE parent = ?";
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (parentId != null) {
                statement.setInt(1, parentId);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Directory directory = mapResultSetToDirectory(resultSet);
                directories.add(directory);
            }
        } catch (SQLException e) {
            System.out.println("[MARIADB] Error al obtener los directorios por parentId: " + e);
        }
        return directories;
    }

    @Override
    public Directory createDirectory(Directory directory) {
        String insertQuery = "INSERT INTO Directory (name, owner, group_id, parent, dirType, permissions, size, contentType, path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, directory.name);
            statement.setInt(2, directory.owner);
            statement.setInt(3, directory.group);
            statement.setObject(4, directory.parent == 0 ? null : directory.parent);
            statement.setString(5, directory.dirType.name());
            statement.setString(6, directory.permissions);
            statement.setString(7, directory.size);
            statement.setString(8, directory.contentType);
            statement.setString(9, directory.path);

            int affectedRows = statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                directory.id = resultSet.getInt(1);
                return directory;
            }
        } catch (SQLException e) {
            System.out.println("[MARIADB] Error al crear el directory " + directory.name + ": " + e);
        }
        return null;
    }

    @Override
    public boolean deleteDirectory(Directory directory) {
        String deleteDirectoryGitQuery = "DELETE FROM DirectoryGit WHERE directory_id = ?";
        String deleteDirectoryQuery = "DELETE FROM Directory WHERE id = ?";

        try {
            if (directory.dirType == DirType.FILE) {
                try (PreparedStatement deleteGitStatement = connection.prepareStatement(deleteDirectoryGitQuery)) {
                    deleteGitStatement.setInt(1, directory.id);
                    deleteGitStatement.executeUpdate();
                }
            }

            try (PreparedStatement deleteDirectoryStatement = connection.prepareStatement(deleteDirectoryQuery)) {
                deleteDirectoryStatement.setInt(1, directory.id);
                int affectedRows = deleteDirectoryStatement.executeUpdate();

                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.out.println("[MARIADB] Error al eliminar el directory " + directory.name + ": " + e);
            return false;
        }
    }

    @Override
    public void createGitVersion(int directoryId, String versionName){
        String insertQuery = "INSERT INTO DirectoryGit (directory_id, name) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setInt(1, directoryId);
            statement.setString(2, versionName);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[MARIADB] Error al crear la versión para el directory ID " + directoryId + ": " + e);
        }
    }

    private Directory mapResultSetToDirectory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int owner = resultSet.getInt("owner");
        int group = resultSet.getInt("group_id");
        Integer parent = resultSet.getObject("parent", Integer.class); // Maneja el caso nulo
        String path = resultSet.getString("path");
        DirType dirType = Objects.equals(resultSet.getString("dirType"), "FILE") ? DirType.FILE:DirType.DIRECTORY;
        String permissions = resultSet.getString("permissions");
        String size = resultSet.getString("size");
        String contentType = resultSet.getString("contentType");
        Date createdAt = resultSet.getDate("createdAt");
        Date updatedAt = resultSet.getDate("updatedAt");
        List<String> tags = getTagsForDirectory(id);

        if (dirType == DirType.DIRECTORY) {
            Folder folder = new Folder();
            folder.id = id;
            folder.name = name;
            folder.owner = owner;
            folder.group = group;
            folder.parent = parent != null ? parent : -1;
            folder.path = path;
            folder.dirType = dirType;
            folder.permissions = permissions;
            folder.size = size != null ? size : "";
            folder.contentType = contentType;
            folder.createdAt = createdAt;
            folder.updatedAt = updatedAt;
            folder.tags = tags;
            folder.children = new ArrayList<>();
            return folder;
        } else {
            Document document = new Document();
            document.id = id;
            document.name = name;
            document.owner = owner;
            document.group = group;
            document.parent = parent != null ? parent : -1;
            document.path = path;
            document.dirType = dirType;
            document.permissions = permissions;
            document.size = size != null ? size : "";
            document.contentType = contentType;
            document.createdAt = createdAt;
            document.updatedAt = updatedAt;
            document.tags = tags;
            document.fileData = null;
            return document;
        }
    }

    private List<String> getTagsForDirectory(int directoryId) {
        List<String> tags = new ArrayList<>();
        String query = "SELECT t.name FROM Tag t " +
                "JOIN DirectoryTag dt ON t.id = dt.tag_id " +
                "WHERE dt.directory_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, directoryId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tags.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("[MARIADB] Error al obtener los tags para el directorio " + directoryId + ": " + e);
        }
        return tags;
    }
}
