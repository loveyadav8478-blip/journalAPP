package com.Origin.journalAPP.service;

import com.Origin.journalAPP.schedular.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    private UserScheduler userScheduler;

    @Test
    public void testFetchUserAndSendEmails(){
        userScheduler.fetchUsersAndSendSaEmails();
    }
}
