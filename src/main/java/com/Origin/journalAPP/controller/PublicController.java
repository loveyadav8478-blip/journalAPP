package com.Origin.journalAPP.controller;

import com.Origin.journalAPP.dto.UserDTO;
import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.service.UserDetailsServiceImpl;
import com.Origin.journalAPP.service.UserService;
import com.Origin.journalAPP.utils.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import javax.swing.text.html.HTML;
import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/public-user")
@Slf4j
@Tag(name="Public APIs",description = "CRUD Operations for User")
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
        log.info("Health is OK!!");
        return "Ok";
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO userDto){
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO){
        try{
            authorizationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUserName(),userDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error Occurred while authentication ",e);
            return new ResponseEntity<>("Incorrect password or username ",HttpStatus.NO_CONTENT);
        }
    }


}
