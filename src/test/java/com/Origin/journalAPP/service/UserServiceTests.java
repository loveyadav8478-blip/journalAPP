package com.Origin.journalAPP.service;

import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

//    @BeforeAll
//    @BeforeEach
//    @AfterAll
//    @AfterEach
//    public void setup(){
//
//    }
    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testFindByUserName(User user){
        assertTrue(userService.saveNewUser(user));
    }

//    @ParameterizedTest
//    @ValueSource(strings = {
//            "Loveyadav",
//            "Abhay",
//            "Yash"
//    })
//    public void testFindByUserName(String name){
//        User user = userRepository.findUserByUserName(name);
//        assertTrue(!user.getJournalEntries().isEmpty());
//    }


    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,8,10",
            "0,1,12"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
