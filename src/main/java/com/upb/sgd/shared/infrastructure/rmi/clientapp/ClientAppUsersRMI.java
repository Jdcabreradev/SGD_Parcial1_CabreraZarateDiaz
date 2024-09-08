package com.upb.sgd.shared.infrastructure.rmi.clientapp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

public interface ClientAppUsersRMI extends Remote {
    public User Login(String username,String password) throws RemoteException;
    public List<User> GetUsers() throws RemoteException;
    public List<Group> GetGroups() throws RemoteException;
}
