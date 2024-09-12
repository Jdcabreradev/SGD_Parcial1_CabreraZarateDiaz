package com.upb.sgd.dataserver.versioncontrol.infrastructure.rmi;

import com.upb.sgd.dataserver.versioncontrol.domain.port.driver.FileSystemUseCasePort;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.infrastructure.rmi.appdata.AppDataRMI;

import java.nio.file.Path;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AppDataRMIService extends UnicastRemoteObject implements AppDataRMI {
    private final FileSystemUseCasePort fileSystemUseCase;

    public AppDataRMIService(FileSystemUseCasePort useCasePort) throws RemoteException{
        this.fileSystemUseCase = useCasePort;
    }


    @Override
    public Folder getRoot() {
        return fileSystemUseCase.getRoot();
    }

    @Override
    public Directory addDirectory(Directory directory, String path) {
        return fileSystemUseCase.addDirectory(directory,path);
    }
}
