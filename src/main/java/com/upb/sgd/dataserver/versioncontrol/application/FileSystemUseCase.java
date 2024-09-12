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
                copyDirectoryPropertiesToFolder(dir, childFolder);  // Copiar todas las propiedades
                rootFolder.AddDirectory(childFolder);
            } else {
                Document document = new Document();
                copyDirectoryPropertiesToDocument(dir, document);  // Copiar todas las propiedades
                rootFolder.AddDirectory(document);
            }
        }
        return rootFolder;
    }

    private Folder createFolderRecursively(Directory dir) {
        Folder folder = new Folder();
        copyDirectoryPropertiesToFolder(dir, folder);

        List<Directory> children = databaseService.findDirByParent(dir.id);

        for (Directory childDir : children) {
            if (childDir.dirType.isFolder()) {
                Folder childFolder = createFolderRecursively(childDir);
                copyDirectoryPropertiesToFolder(childDir, childFolder);
                folder.AddDirectory(childFolder);
            } else {
                Document document = new Document();
                copyDirectoryPropertiesToDocument(childDir, document);
                folder.AddDirectory(document);
            }
        }
        return folder;
    }

    private void copyDirectoryPropertiesToFolder(Directory dir, Folder folder) {
        folder.name = dir.name;
        folder.owner = dir.owner;
        folder.permissions = dir.permissions;
        folder.size = dir.size;
        folder.contentType = dir.contentType;
        folder.path = dir.path;
        folder.tags = dir.tags;
    }

    private void copyDirectoryPropertiesToDocument(Directory dir, Document document) {
        document.name = dir.name;
        document.owner = dir.owner;
        document.permissions = dir.permissions;
        document.size = dir.size;
        document.contentType = dir.contentType;
        document.path = dir.path;
        document.tags = dir.tags;
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

    @Override
    public Document downloadFile(Document document) {
        return null;
    }
}