/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.upb.sgd.appserver.Domain.Port;

import java.util.List;

import com.upb.sgd.shared.domain.Group;
import com.upb.sgd.shared.domain.User;

/**
 *
 * @author sebastian
 */
public interface UserDBProvider {
    public User GetUser(int id);
    public List<User> GetUsers();
    public User Login(String username, String password);
    public Boolean CreateUser(int groupPermId, String username, String password, boolean isAdmin);
    public Boolean DeleteUser(int id);
    public Boolean UpdateUser(int id, List<Integer> groupPermId, String username, String password);
    
    public Group GetGroup(int id);
    public List<Group> GetGroups();
    public Boolean CreateGroup(String name);
    public Boolean DeleteGroup(int id);
    public Boolean UpdateGroup(int id, String name);
}
