package com.upb.sgd.dataserver.versioncontrol.application;

import com.upb.sgd.dataserver.database.domain.port.driven.DatabaseServicePort;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.utils.FileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileSystemUseCase implements FileSystemUseCasePort {
    final DatabaseServicePort databaseService;
    final Path Workdir = Paths.get("/srv/nfs/share");

    public FileSystemUseCase(DatabaseServicePort databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Folder getRoot() {
        Folder rootFolder = new Folder();
        List<Directory> rootChildren = databaseService.findDirByParent(null);
        rootFolder.name = "root";
        for (Directory dir : rootChildren) {
            if (dir.dirType.isFolder()) {
                Folder childFolder = createFolderRecursively(dir);
                rootFolder.AddDirectory(childFolder);
            } else {
                Document document = new Document();
                document.name = dir.name;
                rootFolder.AddDirectory(document);
            }
        }
        return rootFolder;
    }

    private Folder createFolderRecursively(Directory dir) {
        Folder folder = new Folder();
        folder.name = dir.name;
        List<Directory> children = databaseService.findDirByParent(dir.id);
        for (Directory childDir : children) {
            if (dir.dirType.isFolder()) {
                Folder childFolder = createFolderRecursively(childDir);
                folder.AddDirectory(childFolder);
            } else {
                Document document = new Document();
                document.name = childDir.name;
                folder.AddDirectory(document);
            }
        }
        return folder;
    }


    @Override
    public Directory addDirectory(Directory directory, String path) {
        Directory dbDir = databaseService.createDirectory(directory);
        if (dbDir == null){return null;}

       try {
           if (directory.dirType.isFolder()){
               Files.createDirectories(Workdir.resolve(path).resolve(directory.name));
           }else {
               Document dbFile = (Document) dbDir;
               String creationDate = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
               String versionName = FileUtils.SetFileVersionName(directory.name,creationDate);
               Files.createDirectories(Workdir.resolve(path).resolve(directory.name));
               FileUtils.writeByteArrayToFile(Workdir.resolve(path).resolve(directory.name)
                       .resolve(versionName),dbFile.fileData);
               databaseService.createGitVersion(dbFile.id,versionName);
           }
           return dbDir;
       } catch (IOException e) {
           databaseService.deleteDirectory(dbDir);
           System.out.println("[FILESYSTEM] Error al crear directorio "+directory.name+": "+e.toString());
           return null;
       }
    }

    @Override
    public boolean deleteDirectory(Directory directory) {
        if (databaseService.deleteDirectory(directory)) {
            try {
                Path directoryPath = Workdir.resolve(directory.getPath());
                Files.walkFileTree(directoryPath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
                return true;
            } catch (IOException e) {
                System.out.println("[FILESYSTEM] Error al eliminar el directorio " + directory.name + ": " + e.toString());
                return false;
            }
        }
        return false;
    }

}