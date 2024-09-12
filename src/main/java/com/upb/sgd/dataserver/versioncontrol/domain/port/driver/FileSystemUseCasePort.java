package com.upb.sgd.dataserver.versioncontrol.domain.port.driver;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;

public interface FileSystemUseCasePort {
    Folder getRoot();
    Directory addDirectory(Directory directory, String path);
    boolean deleteDirectory(Directory directory);
    Document downloadFile(Document document, String path);
}
