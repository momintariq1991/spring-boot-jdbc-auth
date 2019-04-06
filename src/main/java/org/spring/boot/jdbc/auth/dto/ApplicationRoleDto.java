package org.spring.boot.jdbc.auth.dto;

public class ApplicationRoleDto {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public ApplicationRoleDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApplicationRoleDto setName(String name) {
        this.name = name;
        return this;
    }
}
