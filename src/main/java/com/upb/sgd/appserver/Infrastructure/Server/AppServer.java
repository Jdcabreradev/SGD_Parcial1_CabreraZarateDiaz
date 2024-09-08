package com.upb.sgd.appserver.Infrastructure.Server;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.upb.sgd.appserver.Application.UseCase.UserUseCase;
import com.upb.sgd.appserver.Infrastructure.RMI.UserRouterRMI;
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

    String url = "jdbc:mariadb://localhost:3306/UpbNewsDb?useSSL=false&serverTimezone=UTC";
    String dbUser = "upbnews";
    String dbPassword = "123";
    

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
            System.out.println("Initializing user service.");
            ClientAppUsersRMI usersRMI = new UserRouterRMI(
                new UserUseCase()
            );
            Naming.rebind(uri + "/user", usersRMI);
            System.out.println("User service initialized on: " + uri + "/user");

            //FileSystem endpoint
            
            System.out.println("Appserver ready.");
            return true;    
        } catch (IOException e) {
            System.err.println("Unable to start server: " + e.getMessage());
            return false;
        }
    }
}
