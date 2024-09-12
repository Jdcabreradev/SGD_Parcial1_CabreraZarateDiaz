import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBProvider;
import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBService;
import com.upb.sgd.dataserver.versioncontrol.application.FileSystemUseCase;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class VersionControlUnitTest {
    private static FileSystemUseCasePort fileSystem;
    private static Folder rootFolder;
    private static Folder currentFolder;

    @BeforeAll
    public static void setUp() {
        fileSystem = new FileSystemUseCase(new MariaDBService(MariaDBProvider.MariaDBConn()));
        rootFolder = fileSystem.getRoot();
        currentFolder = rootFolder;
    }

    @Test
    public void testRoot() throws IOException {
        Assertions.assertEquals(rootFolder.name,"root");
        Folder document = (Folder) rootFolder.children.get(7);
        System.out.println(document.name);
        System.out.println(document.dirType);
        System.out.println(document.tags.size());
        System.out.println(document.owner);
        System.out.println(document.group);
        System.out.println(document.permissions);

    }

//    @Test
//    public void testCreateFolderAndDocument() throws IOException {
//        Folder folderToAdd = new Folder();
//        folderToAdd.name = "testFolder";
//        folderToAdd.path = "testFolder";
//        folderToAdd.owner = 1;
//        folderToAdd.group = 1;
//        folderToAdd.parent = currentFolder.id;
//        folderToAdd.permissions = "rwrwrw";
//
//        Folder fsFolder = (Folder) fileSystem.addDirectory(folderToAdd,currentFolder.getPath().toString());
//        if (fsFolder != null){
//            currentFolder.AddDirectory(fsFolder);
//        }
//
//        Assertions.assertEquals(rootFolder.children.getFirst().id, fsFolder != null ? fsFolder.id : 0);
//
//        currentFolder = (Folder) currentFolder.children.getFirst();
//        Folder nestedFolder = new Folder();
//        nestedFolder.name = "nestedFolder";
//        nestedFolder.path = "nestedFolder";
//        nestedFolder.owner = 1;
//        nestedFolder.group = 1;
//        nestedFolder.parent = currentFolder.id;
//        nestedFolder.permissions = "rwrwrw";
//
//        fsFolder = (Folder) fileSystem.addDirectory(nestedFolder,currentFolder.getPath().toString());
//        if (fsFolder != null){
//            currentFolder.AddDirectory(fsFolder);
//        }
//
//        Assertions.assertEquals(currentFolder.id, fsFolder != null ? fsFolder.parent : 0);
//
//        currentFolder = (Folder) currentFolder.children.getFirst();
//        Document newDocument = new Document();
//        newDocument.name = "doc.txt";
//        newDocument.path = "doc.txt";
//        newDocument.contentType = "txt";
//        newDocument.owner = 1;
//        newDocument.group = 1;
//        newDocument.parent = currentFolder.id;
//        newDocument.permissions = "rwrwrw";
//        newDocument.fileData = FileUtils.readFileToByteArray(Paths.get("/srv/nfs/Hola.txt"));
//
//        Document addedDoc = (Document) fileSystem.addDirectory(newDocument,currentFolder.getPath().toString());
//        if (addedDoc != null){
//            currentFolder.AddDirectory(addedDoc);
//        }
//
//        Assertions.assertEquals("doc.txt",currentFolder.children.getFirst().name);
//    }

//    @Test
//    public void deleteTest() throws IOException, InterruptedException {
//        int currentFolderChildren = rootFolder.children.size();
//
//        Folder deleteFolder = new Folder();
//        deleteFolder.name = "deleteFolder";
//        deleteFolder.path = "deleteFolder";
//        deleteFolder.owner = 1;
//        deleteFolder.group = 1;
//        deleteFolder.parent = rootFolder.id;
//        deleteFolder.permissions = "rwrwrw";
//
//        Folder dFolder = (Folder) fileSystem.addDirectory(deleteFolder,rootFolder.getPath().toString());
//        if (dFolder!=null){
//            rootFolder.AddDirectory(dFolder);
//        }
//
//        boolean delete = fileSystem.deleteDirectory(dFolder);
//        Assertions.assertTrue(delete);
//
//        Document newDocument = new Document();
//        newDocument.name = "doc.txt";
//        newDocument.path = "doc.txt";
//        newDocument.contentType = "txt";
//        newDocument.owner = 1;
//        newDocument.group = 1;
//        newDocument.parent = currentFolder.id;
//        newDocument.permissions = "rwrwrw";
//        newDocument.fileData = FileUtils.readFileToByteArray(Paths.get("/srv/nfs/Hola.txt"));
//
//        Document addedDoc = (Document) fileSystem.addDirectory(newDocument,rootFolder.getPath().toString());
//        if (addedDoc != null){
//            currentFolder.AddDirectory(addedDoc);
//        }
//
//        delete = fileSystem.deleteDirectory(addedDoc);
//        Assertions.assertTrue(delete);
//    }


}
