package com.springjwt.Repo;

import com.springjwt.Domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser,Long> {

}
