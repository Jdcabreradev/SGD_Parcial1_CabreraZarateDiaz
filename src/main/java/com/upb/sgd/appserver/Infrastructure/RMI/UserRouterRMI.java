package com.upb.sgd.appserver.Infrastructure.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.upb.sgd.appserver.Domain.Port.UserUseCasePort;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppUsersRMI;

/**
 *
 * @author sebastian
 */
public class UserRouterRMI extends UnicastRemoteObject implements ClientAppUsersRMI {

    private final UserUseCasePort userUseCase;

    public UserRouterRMI(
        UserUseCasePort userUseCase
    ) throws RemoteException{
        this.userUseCase = userUseCase;
    }

    @Override
    public User Login(String username, String password){
        return userUseCase.Login(username, password);
    }

    @Override
    public List<User> GetUsers() {
        return userUseCase.GetUsers();
    }

    @Override
    public User GetUser(int id) throws RemoteException {
        return userUseCase.GetUser(id);
    }

    @Override
    public Boolean CreateUser(int groupPermId, String username, String password, boolean isAdmin) throws RemoteException {
        return userUseCase.CreateUser(groupPermId, username, password, isAdmin);
    }

    @Override
    public Boolean UpdateUser(int id, List<Integer> groupPermId, String username, String password) {
        return userUseCase.UpdateUser(id, groupPermId, username, password);
    }

    //Group Related

    @Override
    public Group GetGroup(int id) throws RemoteException {
        return this.userUseCase.GetGroup(id);
    }

    @Override
    public List<Group> GetGroups() {
        return userUseCase.GetGroups();
    }

    @Override
    public Boolean CreateGroup(String name) throws RemoteException {
        return userUseCase.CreateGroup(name);
    }

    @Override
    public Boolean UpdateGroup(int id, String name) throws RemoteException {
        return userUseCase.UpdateGroup(id, name);
    }
}
