package com.springjwt.Security;

import com.springjwt.Filter.CustomAuthorizationFilters;
import com.springjwt.Filter.customAuthanticatiionfilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userService;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        customAuthanticatiionfilter customAuthanticatiionfilter=new customAuthanticatiionfilter(authenticationManagerBean());
        customAuthanticatiionfilter.setFilterProcessesUrl("/api/login");
      http.csrf().disable();
      http.sessionManagement().sessionCreationPolicy(STATELESS);
      http.authorizeRequests().antMatchers("/api/login/**").permitAll();
      http.authorizeRequests().antMatchers(GET,"/api/users/**").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAuthority("ROLE_ADMIN");
      http.authorizeRequests().anyRequest().authenticated();
      http.addFilter(customAuthanticatiionfilter);
      http.addFilterBefore(new CustomAuthorizationFilters(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
