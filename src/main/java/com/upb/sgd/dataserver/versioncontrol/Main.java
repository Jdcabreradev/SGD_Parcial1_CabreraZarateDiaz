package com.upb.sgd.dataserver.versioncontrol;

import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBProvider;
import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBService;
import com.upb.sgd.dataserver.versioncontrol.application.FileSystemUseCase;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.dataserver.versioncontrol.infrastructure.rmi.AppDataRMIService;
import com.upb.sgd.shared.infrastructure.rmi.appdata.AppDataRMI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        System.setProperty("java.rmi.server.hostname", "25.49.116.249");
        LocateRegistry.createRegistry(Integer.parseInt("1099"));
        FileSystemUseCasePort fileSystem = new FileSystemUseCase(new MariaDBService(MariaDBProvider.MariaDBConn()));
        AppDataRMI service = new AppDataRMIService(fileSystem);
        Naming.rebind("dataserver",service);
        System.out.println("Server is running :D!");
    }
}
