package com.upb.sgd.appserver.Application.UseCase;

import java.util.List;

import com.upb.sgd.appserver.Domain.Port.UserDBProvider;
import com.upb.sgd.appserver.Domain.Port.UserUseCasePort;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

/**
 * @author sebastian
 */
public class UserUseCase implements UserUseCasePort{
    private final UserDBProvider userDBProvider;

    public UserUseCase(
        UserDBProvider userDBProvider
    ){
        this.userDBProvider = userDBProvider;
    }

    @Override
    public User GetUser(int id) {
        return this.userDBProvider.GetUser(id);
    }

    @Override
    public List<User> GetUsers() {
        return  this.userDBProvider.GetUsers();
    }

    @Override
    public User Login(String username, String password) {
        return this.userDBProvider.Login(username, password);
    }

    @Override
    public Boolean CreateUser(int groupPermId, String username, String password) {
        return this.userDBProvider.CreateUser(groupPermId, username, password);
    }

    @Override
    public Boolean DeleteUser(int id) {
        return this.userDBProvider.DeleteUser(id);
    }

    @Override
    public Boolean UpdateUser(int id, List<Integer> groupPermId, String username, String password) {
        return this.userDBProvider.UpdateUser(id, groupPermId, username, password);
    }

    @Override
    public Group GetGroup(int id) {
        return this.userDBProvider.GetGroup(id);
    }

    @Override
    public List<Group> GetGroups() {
        return this.userDBProvider.GetGroups();
    }

    @Override
    public Boolean CreateGroup(String name) {
        return this.userDBProvider.CreateGroup(name);
    }

    @Override
    public Boolean DeleteGroup(int id) {
        return this.userDBProvider.DeleteGroup(id);
    }

    @Override
    public Boolean UpdateGroup(int id, String name) {
        return this.userDBProvider.UpdateGroup(id, name);
    }
}
