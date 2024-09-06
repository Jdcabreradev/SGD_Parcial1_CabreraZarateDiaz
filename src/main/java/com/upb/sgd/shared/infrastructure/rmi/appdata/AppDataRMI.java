package com.upb.sgd.shared.infrastructure.rmi.appdata;

import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.shared.domain.Permissions;

import java.rmi.Remote;
import java.util.List;

public interface AppDataRMI extends Remote {
    public Folder GetFolder(int id);
    public Folder QueryFolder(String args);
    public List<Document> GitDocument(int id);
    public boolean UpdateDirectoryPerms(int id, Permissions user, Permissions group, Permissions other);
    public boolean RestoreDirectory(int id, int version);
}
