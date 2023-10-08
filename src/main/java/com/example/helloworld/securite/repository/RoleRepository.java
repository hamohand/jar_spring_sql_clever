package com.example.helloworld.securite.repository;

import com.example.helloworld.securite.models.ERole;
import com.example.helloworld.securite.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
