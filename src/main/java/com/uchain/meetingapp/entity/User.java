package com.uchain.meetingapp.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String role;
    private String telephone;
    private String registerIp;
    private String lastLoginIp;
}
