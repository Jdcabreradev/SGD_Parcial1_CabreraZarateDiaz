package com.upb.sgd.dataserver.database.infrastructure.mariadb;

import com.upb.sgd.dataserver.database.domain.port.driven.DatabaseServicePort;
import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private Directory mapResultSetToDirectory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int owner = resultSet.getInt("owner");
        int group = resultSet.getInt("group");
        Integer parent = resultSet.getObject("parent", Integer.class); // Maneja el caso nulo
        String path = resultSet.getString("path");
        DirType dirType = DirType.valueOf(resultSet.getString("dirType"));
        String permissions = resultSet.getString("permissions");
        String size = resultSet.getString("size");
        String contentType = resultSet.getString("contentType");
        Date createdAt = resultSet.getDate("createdAt");
        Date updatedAt = resultSet.getDate("updatedAt");
        List<String> tags = getTagsForDirectory(id);

        Directory directory;

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
