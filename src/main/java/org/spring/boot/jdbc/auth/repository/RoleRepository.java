package org.spring.boot.jdbc.auth.repository;

import org.spring.boot.jdbc.auth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
