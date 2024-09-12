package com.upb.sgd.dataserver.versioncontrol.application;

import com.upb.sgd.dataserver.database.domain.port.driven.DatabaseServicePort;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileSystemUseCase implements FileSystemUseCasePort {
    final DatabaseServicePort databaseService;
    final Path Workdir = Paths.get("/srv", "nfs", "share");

    public FileSystemUseCase(DatabaseServicePort databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Folder getRoot(){
        Folder rootFolder = new Folder();
        List<Directory> rootChildren = databaseService.findDirByParent(null);
        rootFolder.name = "root";
        rootFolder.children = rootChildren;
        return rootFolder;
    }
}