package com.upb.sgd.shared.infrastructure.rmi.appdata;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;

public interface AppDataRMI extends Remote {
    public Folder getRoot() throws RemoteException;
    public Directory addDirectory(Directory directory, String path) throws RemoteException;
    public Document downloadFile(Document document, String path) throws RemoteException;
}
