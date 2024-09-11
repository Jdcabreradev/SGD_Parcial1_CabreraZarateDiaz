package com.upb.sgd.dataserver.database.domain.port.driven;

import com.upb.sgd.shared.domain.Directory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DatabaseServicePort {
    void CreateDirectory(Directory directory);
    List<Directory> FindDirectoriesByParent(Integer parentId);
    List<Directory> SearchDirectories(String name, String size, Date date, String permissions, String contentType, List<String> tags);
    boolean DeleteDirectory(Directory directory);
    boolean MoveDirectory(int directoryId, int newParentId);
    boolean updateDirectoryPermissions(int directoryId, String newPermissions);
    boolean updateDirectoryProperties(int directoryId, String newName, String newSize, String newContentType);
    Optional<Directory> findDirectoryById(int directoryId);

    boolean CreateTag(String tagName);
    boolean DeleteTag(int tagId);
    List<String> ListAllTags();
    boolean AddTagToDirectory(int directoryId, int tagId);
    boolean RemoveTagFromDirectory (int directoryId, int tagId);
}
