package com.mokal.springsecurityapp.dto;

import com.mokal.springsecurityapp.entities.enums.Permission;
import com.mokal.springsecurityapp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
