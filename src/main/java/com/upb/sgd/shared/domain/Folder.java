package com.upb.sgd.shared.domain;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Directory {
    public List<Directory> children;

    public Folder() {
        this.dirType = DirType.DIRECTORY;
        this.contentType = "directory";
        this.children = new ArrayList<>();
    }

    public boolean AddDirectory(Directory dir){
        if (dir.parent == this.id){
            dir.parentDirectory = this;
            this.children.add(dir);
            return true;
        }
        for (Directory child : children){
            if (child.dirType.isFolder()){
                Folder folderChild = (Folder) child;
                if (folderChild.AddDirectory(dir)){
                    return true;
                }
            }
        }
        return false;
    }
}