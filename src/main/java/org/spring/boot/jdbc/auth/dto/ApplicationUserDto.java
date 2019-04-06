package org.spring.boot.jdbc.auth.dto;

import java.util.Set;

public class ApplicationUserDto {

    private String id;
    private String username;
    private String password;
    private Set<ApplicationRoleDto> roles;

    public String getId() {
        return id;
    }

    public ApplicationUserDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ApplicationUserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ApplicationUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<ApplicationRoleDto> getRoles() {
        return roles;
    }

    public ApplicationUserDto setRoles(Set<ApplicationRoleDto> roles) {
        this.roles = roles;
        return this;
    }
}
