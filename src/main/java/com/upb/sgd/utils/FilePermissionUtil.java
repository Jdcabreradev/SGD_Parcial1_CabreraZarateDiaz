package com.upb.sgd.utils;

public class FilePermissionUtil {

    public static String generatePermissions(boolean userRead, boolean userWrite,
                                             boolean groupRead, boolean groupWrite,
                                             boolean othersRead, boolean othersWrite) {

        return (userRead ? "r" : "-") +
                (userWrite ? "w" : "-") +
                (groupRead ? "r" : "-") +
                (groupWrite ? "w" : "-") +
                (othersRead ? "r" : "-") +
                (othersWrite ? "w" : "-");
    }

    public static boolean canUserRead(String permissions) {
        return permissions.charAt(0) == 'r';
    }

    public static boolean canUserWrite(String permissions) {
        return permissions.charAt(1) == 'w';
    }

    public static boolean canGroupRead(String permissions) {
        return permissions.charAt(2) == 'r';
    }

    public static boolean canGroupWrite(String permissions) {
        return permissions.charAt(3) == 'w';
    }

    public static boolean canOthersRead(String permissions) {
        return permissions.charAt(4) == 'r';
    }

    public static boolean canOthersWrite(String permissions) {
        return permissions.charAt(5) == 'w';
    }
}

