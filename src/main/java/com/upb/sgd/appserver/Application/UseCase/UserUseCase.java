/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver.Application.UseCase;

import java.util.List;

import com.upb.sgd.appserver.Domain.Port.UserUseCasePort;
import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

/**
 * @author sebastian
 */
public class UserUseCase implements UserUseCasePort{

    @Override
    public User GetUser(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> GetUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User Login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean CreateUser(int type, String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean DeleteUser(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean UpdateUser(int id, int type, String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Group GetGroup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Group> GetGroups() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean CreateGroup(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean DeleteGroup(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean UpdateGroup(int id, String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
