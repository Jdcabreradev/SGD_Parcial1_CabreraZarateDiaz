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
    public List<Group> GetGroups() {
        return userUseCase.GetGroups();
    }
}
