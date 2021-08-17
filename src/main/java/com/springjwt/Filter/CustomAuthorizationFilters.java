package com.springjwt.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.resolve;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilters extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().equals("/api/login")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else {
            String authorizationHeader=httpServletRequest.getHeader(AUTHORIZATION);
            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
               try {
                   String token=authorizationHeader.substring("Bearer ".length());
                   Algorithm algorithm=Algorithm.HMAC256("secret".getBytes());
                   JWTVerifier verifier= JWT.require(algorithm).build();
                   DecodedJWT decodedJWT=verifier.verify(token);
                   String username=decodedJWT.getSubject();
                   String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                   Collection <SimpleGrantedAuthority> authorities=new ArrayList<>();
                   stream(roles).forEach(role->{
                       authorities.add(new SimpleGrantedAuthority(role));
                   });
                   UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                   filterChain.doFilter(httpServletRequest,httpServletResponse);
               }catch (Exception e){
                   log.error("Error Login {}",e.getMessage());
                   httpServletResponse.setHeader("Error",e.getMessage());
                   httpServletResponse.setStatus(FORBIDDEN.value());
                 //  httpServletResponse.sendError(FORBIDDEN.value());

                   Map<String,String> error=new HashMap<>();
                   error.put("error_messege",e.getMessage());
                   httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
                   new ObjectMapper().writeValue(httpServletResponse.getOutputStream(),error);
               }
            }else{
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }
        }
    }
}