/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.appserver.Application.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.infrastructure.rmi.appdata.AppDataRMI;
import com.upb.sgd.shared.infrastructure.rmi.clientapp.ClientAppDirectoryRMI;

/**
 *
 * @author sebastian
 */
public class AppDataService extends UnicastRemoteObject implements ClientAppDirectoryRMI{
    private final String url;
    private AppDataRMI dataService;

    public AppDataService(String url) throws RemoteException{
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
    public Folder getRoot() throws RemoteException {
        return this.dataService.getRoot();
    }

    @Override
    public Directory addDirectory(Directory directory, String path) throws RemoteException {
        return this.dataService.addDirectory(directory, path);
    }
}
