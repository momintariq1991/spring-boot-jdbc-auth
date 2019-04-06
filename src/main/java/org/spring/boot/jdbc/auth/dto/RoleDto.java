package org.spring.boot.jdbc.auth.dto;

public class RoleDto {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public RoleDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleDto setName(String name) {
        this.name = name;
        return this;
    }
}
