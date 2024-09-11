package com.upb.sgd.shared.domain;

public enum DirType {
    FILE(true),
    FOLDER(false);

    private final boolean isDir;

    DirType(boolean isDir) {
        this.isDir = isDir;
    }

    public boolean isFile() {
        return isDir;
    }

    public static DirType fromBoolean(boolean isFile) {
        return isFile ? FILE : FOLDER;
    }
}

