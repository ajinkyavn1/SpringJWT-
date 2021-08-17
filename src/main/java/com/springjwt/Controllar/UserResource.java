package com.springjwt.Controllar;

import com.springjwt.Domain.Role;
import com.springjwt.Domain.User;
import com.springjwt.Service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser(){
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toString());
        return  ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role>saveUser(@RequestBody Role role){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toString());
        return  ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addTouser(@RequestBody RoleTouser from){
        userService.addRoleToUser(from.username,from.getRoleName());
        return  ResponseEntity.ok().build();
    }
    @Data
    private class RoleTouser {
        private String username;
        private  String roleName;

    }
}
