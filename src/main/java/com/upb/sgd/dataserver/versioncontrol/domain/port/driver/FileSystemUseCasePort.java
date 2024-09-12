package com.upb.sgd.dataserver.versioncontrol.domain.port.driver;

import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Folder;

import java.nio.file.Path;

public interface FileSystemUseCasePort {
    Folder getRoot();
}
