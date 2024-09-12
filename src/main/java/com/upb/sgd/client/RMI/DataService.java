/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upb.sgd.client.RMI;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppDirectoryRMI;

/**
 *
 * @author sebastian
 */
public class DataService {

    private final String url;
    private ClientAppDirectoryRMI dataRMI;
    public Folder rootFolder;
    public Folder currentFolder;

    public DataService(String url) {
        this.url = url;
    }

    public boolean Init() {
        try {
            this.dataRMI = (ClientAppDirectoryRMI) Naming.lookup(this.url);
            return true;
        } catch (IOException | NotBoundException e) {
            System.out.println("Unable to bind user service: " + e.getMessage());
            return false;
        }
    }

    public void GetRoot() {
        try {
            this.rootFolder = this.dataRMI.getRoot();
            this.currentFolder = rootFolder;
        } catch (IOException e) {
            System.out.println("Unable to retrieve remote root");
        }
    }

    public void UploadFile(Directory file, String path){
        try {
            this.dataRMI.addDirectory(file,path);
        } catch (RemoteException e) {
            System.out.println("Unable to upload file" + e.getMessage());
        }
    }

    public void CreateFolder(Directory folder,String path){
        try {
            this.dataRMI.addDirectory(folder,path);
        } catch (RemoteException e) {
            System.out.println("Unable to create folder" + e.getMessage());
        }
    }

    public Document DownloadFile(Document document, String path){
        try {
            return this.dataRMI.downladFile(document,path);
        } catch (RemoteException e) {
            System.out.println("no se pudo descargar el archivo");
            return  document;
        }
    }

    public Folder SearchForDocuments(String query) {
        Folder result = new Folder();
        result.name = "Query";
        SearchFolder(rootFolder, query, result.children);
        return result;
    }

    private void SearchFolder(Folder folder, String query, List<Directory> result) {
        for (Directory dir : folder.children) {
            switch (dir) {
                case Document doc -> {
                    boolean tmpAccept = false;

                    if (doc.name != null && (doc.name.toLowerCase().contains(query.toLowerCase())
                            || doc.name.toLowerCase().equals(query.toLowerCase()))) {
                        tmpAccept = true;
                    }

                    for (String tag : doc.tags) {
                        if (tag != null && tag.toLowerCase().contains(query.toLowerCase())) {
                            tmpAccept = true;
                        }
                    }

                    if (doc.createdAt != null && doc.createdAt.toString().contains(query)) {
                        tmpAccept = true;
                    }

                    if (doc.updatedAt != null && doc.updatedAt.toString().contains(query)) {
                        tmpAccept = true;
                    }

                    if (tmpAccept) {
                        result.add(doc);
                    }

                }
                case Folder folder1 ->
                    SearchFolder(folder1, query, result);
                default -> {
                }
            }
        }
    }

    String randomSize() {
        return (100 + (int) (Math.random() * 900)) + " MB";
    }

    String randomPermissions() {
        return "rw-r--r--";
    }

    Date createDate(int year, int month, int day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(String.format("%d-%02d-%02d", year, month, day));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return new Date(); // Default to current date in case of error
        }
    }

    public void loadFileSystemExample() {
        // Create root folder
        rootFolder = new Folder();
        rootFolder.name = "Root";
        rootFolder.path = "/root";
        rootFolder.dirType = DirType.FILE;
        rootFolder.permissions = randomPermissions();
        rootFolder.size = "0 KB";
        rootFolder.createdAt = createDate(2024, 1, 1);
        rootFolder.updatedAt = new Date();
        rootFolder.tags = new ArrayList<>();

        // Create folders
        Folder folder1 = new Folder();
        folder1.name = "Folder 1";
        folder1.path = "/root/Folder1";
        folder1.dirType = DirType.FILE;
        folder1.permissions = randomPermissions();
        folder1.size = "0 KB";
        folder1.createdAt = createDate(2024, 2, 1);
        folder1.updatedAt = new Date();
        folder1.tags = new ArrayList<>();

        Folder folder2 = new Folder();
        folder2.name = "Folder 2";
        folder2.path = "/root/Folder2";
        folder2.dirType = DirType.FILE;
        folder2.permissions = randomPermissions();
        folder2.size = "0 KB";
        folder2.createdAt = createDate(2024, 3, 1);
        folder2.updatedAt = new Date();
        folder2.tags = new ArrayList<>();

        Folder folder3 = new Folder();
        folder3.name = "Folder 3";
        folder3.path = "/root/Folder3";
        folder3.dirType = DirType.FILE;
        folder3.permissions = randomPermissions();
        folder3.size = "0 KB";
        folder3.createdAt = createDate(2024, 4, 1);
        folder3.updatedAt = new Date();
        folder3.tags = new ArrayList<>();

        // Create files for folders
        Document file1 = new Document();
        file1.name = "File 1-1";
        file1.path = "/root/Folder1/File1-1";
        file1.dirType = DirType.FILE;
        file1.permissions = randomPermissions();
        file1.size = randomSize();
        file1.createdAt = createDate(2024, 2, 10);
        file1.updatedAt = new Date();
        file1.tags = new ArrayList<>();

        Document file2 = new Document();
        file2.name = "File 2-1";
        file2.path = "/root/Folder2/File2-1";
        file2.dirType = DirType.FILE;
        file2.permissions = randomPermissions();
        file2.size = randomSize();
        file2.createdAt = createDate(2024, 3, 10);
        file2.updatedAt = new Date();
        file2.tags = new ArrayList<>();

        Document file3 = new Document();
        file3.name = "File 3-1";
        file3.path = "/root/Folder3/File3-1";
        file3.dirType = DirType.FILE;
        file3.permissions = randomPermissions();
        file3.size = randomSize();
        file3.createdAt = createDate(2024, 4, 10);
        file3.updatedAt = new Date();
        file3.tags = new ArrayList<>();

        Document file4 = new Document();
        file4.name = "File 3-2";
        file4.path = "/root/Folder3/File3-2";
        file4.dirType = DirType.FILE;
        file4.permissions = randomPermissions();
        file4.size = randomSize();
        file4.createdAt = createDate(2024, 4, 15);
        file4.updatedAt = new Date();
        file4.tags = new ArrayList<>();

        Document file5 = new Document();
        file5.name = "File 3-3";
        file5.path = "/root/Folder3/File3-3";
        file5.dirType = DirType.FILE;
        file5.permissions = randomPermissions();
        file5.size = randomSize();
        file5.createdAt = createDate(2024, 4, 5);
        file5.updatedAt = new Date();
        file5.tags = new ArrayList<>();
        file5.tags.add("alpha");
        file5.tags.add("beta");
        file5.tags.add("gamma");
        file5.tags.add("delta");
        file5.tags.add("epsilon");

        // Create a nested folder
        Folder nestedFolder = new Folder();
        nestedFolder.name = "Nested Folder";
        nestedFolder.path = "/root/Folder3/NestedFolder";
        nestedFolder.dirType = DirType.FILE;
        nestedFolder.permissions = randomPermissions();
        nestedFolder.size = "0 KB";
        nestedFolder.createdAt = createDate(2024, 4, 20);
        nestedFolder.updatedAt = new Date();
        nestedFolder.tags = new ArrayList<>();
        nestedFolder.AddDirectory(file4); // Add file to the nested folder

        // Insert nested folder into folder 3
        folder3.AddDirectory(nestedFolder);
        folder3.AddDirectory(file3); // Add file to folder 3
        folder3.AddDirectory(file5);

        // Insert files into folders
        folder1.AddDirectory(file1);
        folder2.AddDirectory(file2);

        // Insert folders into root
        rootFolder.AddDirectory(folder1);
        rootFolder.AddDirectory(folder2);
        rootFolder.AddDirectory(folder3);
    }
}
