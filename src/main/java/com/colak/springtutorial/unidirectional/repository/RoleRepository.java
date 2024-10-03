package com.colak.springtutorial.unidirectional.repository;


import com.colak.springtutorial.unidirectional.jpa.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}

