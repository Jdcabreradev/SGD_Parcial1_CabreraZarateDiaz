package com.upb.sgd.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class Directory implements Serializable {
    int id;
    String name;
    String size;
    Permissions userPerm;
    Permissions groupPerm;
    Permissions otherPerm;
    Date createdAt;
    Date updatedAt;
    List<String> tags;
    String appRoute;
    int type;
    int parent;
    Directory parentDirectory;
}