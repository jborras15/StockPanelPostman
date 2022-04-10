package com.jb.springdata.entity;

import lombok.Data;

@Data
public class Password {

    private String email;
    private String oldPassword;
    private String newPassword;
}
