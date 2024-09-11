package com.upb.sgd.appserver.Infrastructure.Server;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.upb.sgd.appserver.Application.Service.AppDataService;
import com.upb.sgd.appserver.Application.UseCase.UserUseCase;
import com.upb.sgd.appserver.Infrastructure.Provider.MariaDBProvider;
import com.upb.sgd.appserver.Infrastructure.RMI.UserRouterRMI;
import com.upb.sgd.appserver.Infrastructure.Socket.ServerProcess;
import com.upb.sgd.appserver.Infrastructure.Socket.ServerSocket;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppUsersRMI;

/**
 * @author sebastian
 */
public class AppServer {
    private final String ip;
    private final String port;
    private final String serviceName;
    private final String uri;

    private Connection connection;

    public ClientAppUsersRMI usersRMI;
    public ServerProcess serverProcess;
    public AppDataService dataService;

    String url = "jdbc:mariadb://localhost:3306/SGDUSERDB?useSSL=false&serverTimezone=UTC";
    String dbUser = "APPSERVERUSER";
    String dbPassword = "123";


    String dataServerRMIurl = "rmi://127.0.0.2/data";
    
    public AppServer(String ip, String port, String serviceName){
        this.ip = ip;
        this.port = port;
        this.serviceName = serviceName;
        this.uri = "rmi://" + this.ip + ":" + this.port + "/" + this.serviceName;

        try {
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Appserver connected to database.");
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        } 
    }

    public boolean Init(){
        try {
            //RMI SetUp
            System.out.println("Initializing " + serviceName + " server.");
            System.setProperty("java.rmi.server.hostname", ip);
            LocateRegistry.createRegistry(Integer.parseInt(port));
            
            //User endpoint setup
            System.out.println("Initializing user endpoint.");
            usersRMI = new UserRouterRMI(
                new UserUseCase(
                    new MariaDBProvider(connection)
                )
            );
            Naming.rebind(uri + "/user", usersRMI);
            System.out.println("User service endpoint on: " + uri + "/user");

            //FileSystem endpoint
            System.out.println("Initializing data service.");
            this.dataService = new AppDataService(this.dataServerRMIurl);
            if(this.dataService.Init()){
                System.out.println("Connection to data server stablished");
                Naming.rebind(uri + "/data", dataService);
            }else{
                System.err.println("Unable to connect to data server");
            }
            
            //Notification Socket Process
            System.out.println("Initializing notification socket module.");
            this.serverProcess = new ServerProcess(
                new ServerSocket(1803, 100)
            );
            serverProcess.Init();

            System.out.println("Appserver ready.");
            return true;    
        } catch (IOException e) {
            System.err.println("Unable to start server: " + e.getMessage());
            return false;
        }
    }
}
