/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.upb.sgd.client.RMI;

 import java.io.IOException;
 import java.rmi.Naming;
 import java.rmi.NotBoundException;
 import java.rmi.RemoteException;
 import java.util.List;
 
 import com.upb.sgd.shared.domain.Group;
 import com.upb.sgd.shared.domain.User;
 import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppUsersRMI;
 
 /**
  *
  * @author sebastian
  */
 public class UserService{
     private final String url;
     private ClientAppUsersRMI usersRMI;
 
     public UserService(String url){
         this.url = url;
     }
 
     public boolean Init(){
         try {
             this.usersRMI = (ClientAppUsersRMI) Naming.lookup(url);
             return true;
         } catch (IOException | NotBoundException e) {
             System.out.println("Unable to bind user service: " + e.getMessage());
             return false;
         }
     }
 
     public User GetUser(int id) throws RemoteException {
         throw new UnsupportedOperationException("Not supported yet.");
     }
 
     public List<User> GetUsers() throws RemoteException {
         return usersRMI.GetUsers();
     }
 
     public User Login(String username, String password) throws RemoteException {
         return usersRMI.Login(username, password);
     }
 
     public Boolean CreateUser(int groupPermId, String username, String password, boolean isAdmin) throws RemoteException {
         return usersRMI.CreateUser(groupPermId, username, password, isAdmin);
     }
 
     public Boolean UpdateUser(int id, List<Integer> groupPermId, String username, String password) throws RemoteException {
         return usersRMI.UpdateUser(id, groupPermId, username, password);
     }
 
     public List<Group> GetGroups() throws RemoteException {
         return usersRMI.GetGroups();
     }
 
     public Group GetGroup(int id) throws RemoteException {
         throw new UnsupportedOperationException("Not supported yet.");
     }
 
     public Boolean CreateGroup(String name) throws RemoteException {
         return usersRMI.CreateGroup(name);
     }
 
     public Boolean UpdateGroup(int id, String name) throws RemoteException {
         return usersRMI.UpdateGroup(id, name);
     }
 }
 