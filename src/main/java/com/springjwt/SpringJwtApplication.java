package com.springjwt;

import com.springjwt.Domain.Role;
import com.springjwt.Domain.User;
import com.springjwt.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(9);
    }
//    @Bean
//    CommandLineRunner run(UserService userService){
//        return  args ->{
//          userService.saveRole(new Role(null,"ROLE_USER"));
//            userService.saveRole(new Role(null,"ROLE_MANAGER"));
//            userService.saveRole(new Role(null,"ROLE_ADMIN"));
//            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//
//
//            userService.saveUser(new User(null,"Ajinkya nar","Ajinkya","123456",new ArrayList<>()));
//            userService.saveUser(new User(null,"laukik sf","laukik","123456",new ArrayList<>()));
//            userService.saveUser(new User(null,"vijaykumar  nar","vijaykumar","123456",new ArrayList<>()));
//            userService.saveUser(new User(null,"adwait nar","adwait","123456",new ArrayList<>()));
//            userService.addRoleToUser("Ajinkya","ROLE_USER");
//            userService.addRoleToUser("laukik","ROLE_USER");
//            userService.addRoleToUser("Ajinkya","ROLE_MANAGER");
//            userService.addRoleToUser("laukik","ROLE_MANAGER");
//            userService.addRoleToUser("vijaykumar","ROLE_ADMIN");
//            userService.addRoleToUser("adwait","ROLE_SUPER_ADMIN");
//
//
//        };
//    }
}
