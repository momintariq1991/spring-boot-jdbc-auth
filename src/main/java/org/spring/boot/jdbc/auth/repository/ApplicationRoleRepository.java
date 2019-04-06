package org.spring.boot.jdbc.auth.repository;

import org.spring.boot.jdbc.auth.domain.ApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRoleRepository extends JpaRepository<ApplicationRole, Integer> {
}
