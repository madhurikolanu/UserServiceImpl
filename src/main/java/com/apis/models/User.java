package com.apis.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roleList;
    private String hashedPassword;
    private boolean isEmailVerified;
}
