package com.upb.sgd.shared.domain;

public enum DirType {
    FILE(false),
    DIRECTORY(true);

    private final boolean isDir;

    DirType(boolean isDir) {
        this.isDir = isDir;
    }

    public boolean isFile() {
        return !isDir;
    }

    public  boolean isFolder(){
        return  isDir;
    }

    public static DirType fromBoolean(boolean isFile) {
        return isFile ? DIRECTORY : FILE;
    }
}

