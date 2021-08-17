package com.springjwt.Repo;

import com.springjwt.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo  extends JpaRepository<Role,Long> {
    Role findByName(String username);
}
