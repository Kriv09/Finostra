package com.example.finostra.Repositories;

import com.example.finostra.Entity.Roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}