package com.Origin.journalAPP.service;

import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUsername() {
        when(userRepository.findUserByUserName(anyString())).thenReturn(User.builder()
                                .userName("Loveyadav")
                                .password("jnk")
                                .roles(new ArrayList<>())
                                .build()
        );
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("Loveyadav");
        Assertions.assertNotNull(user);
    }
}
