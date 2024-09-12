package com.upb.sgd.shared.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    public int Id;
    public String username;
    public String password;
    public boolean isAdmin;
    public List<Integer> groupPermIds; 
}