package com.apis.security.models;

import com.apis.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {
    private String role;

    CustomGrantedAuthority(Role role){
        this.role = role.getValue();
    }
    @Override
    public String getAuthority() {
        return role;
    }
}
