package com.upb.sgd.shared.infrastructure.rmi.appdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;

public interface AppDataRMI extends Remote {
    public Folder GetFolder(int id) throws RemoteException;
    public Folder QueryFolder(String args) throws RemoteException;
    public List<Document> GitDocument(int id) throws RemoteException;
    public boolean UpdateDirectoryPerms(int id, Permissions user, Permissions group, Permissions other) throws RemoteException;
    public boolean RestoreDirectory(int id, int version) throws RemoteException;
}
