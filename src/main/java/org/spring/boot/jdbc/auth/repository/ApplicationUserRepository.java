package org.spring.boot.jdbc.auth.repository;

import org.spring.boot.jdbc.auth.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {

    Optional<ApplicationUser> findByUsername(String username);
}
