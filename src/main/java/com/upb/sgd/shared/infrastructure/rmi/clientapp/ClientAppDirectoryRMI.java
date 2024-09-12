/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.upb.sgd.shared.infrastructure.rmi.clientapp;

import java.nio.file.Path;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Folder;

/**
 *
 * @author sebastian
 */
public interface ClientAppDirectoryRMI extends Remote{
    public Folder getRoot() throws RemoteException;
    public Directory addDirectory(Directory directory, String path) throws RemoteException;
}
