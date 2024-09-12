package com.upb.sgd.dataserver.versioncontrol.domain.port.driver;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Folder;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileSystemUseCasePort extends Remote {
    Folder getRoot() throws RemoteException;
    Directory addDirectory(Directory directory, Path path) throws RemoteException;
}
