package com.upb.sgd.shared.infrastructure.rmi.clientapp;

import com.upb.sgd.shared.domain.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientAppRMI extends Remote {
    public User Login(String username,String password) throws RemoteException;
    public List<User> GetUsers();
    public List<Group> GetGroups();
    public Folder GetFolder(int id);
    public Folder QueryFolder(String args);
    public List<Document> GitDocument(int id);
    public boolean UpdateDirectoryPerms(int id,Permissions user, Permissions group, Permissions other);
    public boolean RestoreDirectory(int id, int version);
}
