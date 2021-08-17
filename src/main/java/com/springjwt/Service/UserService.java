package com.springjwt.Service;

import com.springjwt.Domain.Role;
import com.springjwt.Domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);
    User getUser(String username);
    List<User> getUsers();
}
