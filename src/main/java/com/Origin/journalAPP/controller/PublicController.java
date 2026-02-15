package com.Origin.journalAPP.controller;

import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.service.UserDetailsServiceImpl;
import com.Origin.journalAPP.service.UserService;
import com.Origin.journalAPP.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/public-user")
@Slf4j
public class PublicController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authorizationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/health-check")
    public String healthCheck(){
        System.out.println("Health is OK!!");
        return "Ok";
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error Occurred while authentication ",e);
            return new ResponseEntity<>("Incorrect password or username ",HttpStatus.NO_CONTENT);
        }
    }


}
