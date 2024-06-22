package com.apis.security.models;

import com.apis.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    CustomGrantedAuthority(Role role){
        this.authority = role.getValue();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
