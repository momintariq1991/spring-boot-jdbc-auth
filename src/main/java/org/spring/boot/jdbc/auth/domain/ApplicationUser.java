package org.spring.boot.jdbc.auth.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application_user")
public class ApplicationUser {

    @Id
    private String id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "applicationUser", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ApplicationRole> applicationRoles;

    public ApplicationUser() {}

    public ApplicationUser(ApplicationUser applicationUser) {
        this.id = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.applicationRoles = applicationUser.getApplicationRoles();
    }

    public String getId() {
        return id;
    }

    public ApplicationUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ApplicationUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ApplicationUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<ApplicationRole> getApplicationRoles() {
        return applicationRoles;
    }

    public ApplicationUser setApplicationRoles(Set<ApplicationRole> applicationRoles) {
        this.applicationRoles = applicationRoles;
        return this;
    }

    public void addRole(ApplicationRole applicationRole) {
        if (this.getApplicationRoles() == null) {
            this.setApplicationRoles(new HashSet<>());
        }
        this.getApplicationRoles().add(applicationRole);
        applicationRole.setApplicationUser(this);
    }

    public void removeRole(ApplicationRole applicationRole) {
        if (this.getApplicationRoles() != null) {
            this.getApplicationRoles().remove(applicationRole);
            applicationRole.setApplicationUser(null);
        }
    }
}
