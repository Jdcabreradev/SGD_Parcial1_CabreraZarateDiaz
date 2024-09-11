/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver.Application.Service;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;
import com.upb.sgd.shared.infrastructure.rmi.appdata.AppDataRMI;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppDirectoryRMI;

/**
 *
 * @author sebastian
 */
public class AppDataService implements ClientAppDirectoryRMI{
    private final String url;
    private AppDataRMI dataService;

    public AppDataService(String url){
        this.url = url;
    }

    public boolean Init(){
        try {
            this.dataService = (AppDataRMI) Naming.lookup(this.url);  
            return true;
        } catch (IOException | NotBoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Folder GetFolder(int id) {
        try {
            return this.dataService.GetFolder(id);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Folder QueryFolder(String args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Document> GitDocument(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean UpdateDirectoryPerms(int id, Permissions user, Permissions group, Permissions other) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean RestoreDirectory(int id, int version) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
