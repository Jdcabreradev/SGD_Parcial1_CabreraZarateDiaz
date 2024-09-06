package com.upb.sgd.shared.domain;

import java.io.Serializable;

public class User implements Serializable {
    int Id;
    String username;
    String password;
    int groupPermId;
}