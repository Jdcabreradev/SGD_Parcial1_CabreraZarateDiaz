package com.upb.sgd.shared.infrastructure.rmi.clientapp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

public interface ClientAppUsersRMI extends Remote {
    public User GetUser(int id) throws RemoteException;
    public List<User> GetUsers() throws RemoteException;
    public User Login(String username, String password) throws RemoteException;
    public Boolean CreateUser(int groupPermId, String username, String password) throws RemoteException;;
    //public Boolean DeleteUser(int id) throws RemoteException;
    public Boolean UpdateUser(int id, List<Integer> groupPermId, String username, String password);

    public List<Group> GetGroups() throws RemoteException;
    public Group GetGroup(int id) throws RemoteException;
    public Boolean CreateGroup(String name) throws RemoteException;
    //public Boolean DeleteGroup(int id) throws RemoteException;
    public Boolean UpdateGroup(int id, String name) throws RemoteException;
}
