package com.upb.sgd.dataserver.versioncontrol;

import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBProvider;
import com.upb.sgd.dataserver.database.infrastructure.mariadb.MariaDBService;
import com.upb.sgd.dataserver.versioncontrol.application.FileSystemUseCase;
import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        FileSystemUseCasePort fileSystem = new FileSystemUseCase(new MariaDBService(MariaDBProvider.MariaDBConn()));

        Registry dataServer = LocateRegistry.createRegistry(1099);
        dataServer.bind("/dataserver",fileSystem);

        System.out.println("Server is running :D!");
    }
}
