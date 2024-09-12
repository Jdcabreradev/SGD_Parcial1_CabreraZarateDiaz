package com.upb.sgd.shared.domain;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Directory implements Serializable {
    // Identity
    public int id;
    public String name;
    public int owner;
    public int group;
    public int parent;

    // Properties
    public String path;
    public DirType dirType;
    public String permissions;
    public String size;
    public String contentType;

    // Metadata
    public Date createdAt;
    public Date updatedAt;
    public List<String> tags;

    // Others
    public Directory parentDirectory;

    public Directory(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Path getPath() {
        List<String> pathSegments = new ArrayList<>();
        collectPathSegments(this, pathSegments);
        return Paths.get(String.join("/", pathSegments));
    }

    private void collectPathSegments(Directory directory, List<String> pathSegments) {
        if (directory.parentDirectory != null) {
            collectPathSegments(directory.parentDirectory, pathSegments);
        }
        pathSegments.add(directory.name);
    }

        @Override
    public String toString() {
        return this.name;
    }
}
