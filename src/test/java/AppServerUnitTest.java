/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.upb.sgd.appserver.Infrastructure.Server.AppServer;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

/**
 *
 * @author sebastian
 */
public class AppServerUnitTest {
    private static AppServer appserver;
    
    @BeforeAll
    public static void setUp() throws RemoteException {
        appserver = new AppServer("127.0.0.1", "1802", "appserver");
        appserver.Init();
    }

    @Test
    public void testLogin() throws RemoteException {
        User result = appserver.usersRMI.Login("utest", "123");
        for (int i = 0; i < result.groupPermIds.size(); i++) {
            System.out.println(result.groupPermIds.get(i));
        }
        
        assertEquals(result.username, "utest");
    }

    @Test
    public void testGetUsers() throws RemoteException{
        List<User> result = appserver.usersRMI.GetUsers();
        System.out.println(result.getFirst().username);
        assertEquals(result.size(), 1); //1 should change to DB # of users
    }

    @Test
    public void testGetGroups() throws RemoteException{
        List<Group> result = appserver.usersRMI.GetGroups();
        System.out.println(result.getFirst().Name);
        assertEquals(result.size(), 1); //1 should change to DB # of groups
    }
}