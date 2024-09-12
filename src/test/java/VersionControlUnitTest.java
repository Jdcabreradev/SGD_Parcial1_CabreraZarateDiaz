import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBProvider;
import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBService;
import com.upb.sgd.dataserver.versioncontrol.application.FileSystemUseCase;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.shared.domain.Folder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VersionControlUnitTest {
    private static FileSystemUseCasePort fileSystem;
    private static Folder rootFolder;

    @BeforeAll
    public static void setUp(){
        fileSystem = new FileSystemUseCase(new MariaDBService(MariaDBProvider.MariaDBConn()));
    }

    @Test
    public void testRoot(){
        rootFolder = fileSystem.getRoot();
        Assertions.assertEquals(rootFolder.name,"root");
    }
}
