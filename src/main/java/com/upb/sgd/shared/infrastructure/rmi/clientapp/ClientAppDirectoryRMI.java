/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.upb.sgd.shared.infrastructure.rmi.clientapp;

import java.rmi.Remote;
import java.util.List;

import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;

/**
 *
 * @author sebastian
 */
public interface ClientAppDirectoryRMI extends Remote{
    public Folder GetFolder(int id);
    public Folder QueryFolder(String args);
    public List<Document> GitDocument(int id);
    public boolean UpdateDirectoryPerms(int id,Permissions user, Permissions group, Permissions other);
    public boolean RestoreDirectory(int id, int version);
}
