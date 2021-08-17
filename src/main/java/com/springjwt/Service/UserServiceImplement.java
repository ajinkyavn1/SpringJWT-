package com.springjwt.Service;

import com.springjwt.Domain.Role;
import com.springjwt.Domain.User;
import com.springjwt.Repo.UserRepo;
import com.springjwt.Repo.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplement implements  UserService, UserDetailsService {

    private  final UserRepo userRepo;
    private  final UserRoleRepo userRoleRepo;
    private  final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username);
        if(user==null){
            log.error("User is not found in database ");
            throw new UsernameNotFoundException("User is not found in database");
        }else{
            log.info("User Found in database");
        }
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
    @Override
    public User saveUser(User user) {
        log.info("Saving new user to database");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database",role.getName());
        return userRoleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user=userRepo.findByUsername(username);
        Role role=userRoleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
       return  userRepo.findAll();
    }


}
