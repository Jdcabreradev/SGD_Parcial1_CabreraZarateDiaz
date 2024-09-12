package com.upb.sgd.dataserver.database.domain.port.driven;

import com.upb.sgd.shared.domain.Directory;

import java.util.List;

public interface DatabaseServicePort {
    List<Directory> findDirByParent(Integer parentId);
    Directory createDirectory(Directory directory);
    boolean deleteDirectory(Directory directory);

    String getLatestVersionName(int directoryId);
    void createGitVersion(int directoryId,String versionName);
}
