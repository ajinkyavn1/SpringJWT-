package com.springjwt.Repo;

import com.springjwt.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);


}
