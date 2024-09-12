package com.upb.sgd.shared.infrastructure.rmi.appdata;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppDataRMI extends Remote {
    public Folder getRoot() throws RemoteException;
    public Directory addDirectory(Directory directory, Path path) throws RemoteException;
}
