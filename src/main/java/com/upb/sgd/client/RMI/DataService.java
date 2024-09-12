/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.RMI;

import java.rmi.RemoteException;
import java.util.List;

import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppDirectoryRMI;

/**
 *
 * @author sebastian
 */
public class DataService{
    private final String url;
    private ClientAppDirectoryRMI usersRMI;
    public Folder root;

    public DataService(String url){
        this.url = url;
    }
    
    public boolean Init(){
        /*try {
            this.usersRMI = (ClientAppDirectoryRMI) Naming.lookup(this.url);
            return true;
        } catch (IOException | NotBoundException e) {
            System.out.println("Unable to bind user service: " + e.getMessage());
            return false;
        }*/
       return false;
    }

    public Folder GetFolder(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Folder QueryFolder(String args) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Document> GitDocument(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean UpdateDirectoryPerms(int id, Permissions user, Permissions group, Permissions other) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean RestoreDirectory(int id, int version) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
