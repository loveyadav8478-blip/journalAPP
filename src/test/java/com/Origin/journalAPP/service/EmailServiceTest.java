package com.Origin.journalAPP.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmail(){
//        emailService.sendEmailWithPhoto("loveyadavrajakataal@gmail.com","Testing email sender","Hello Ji","C:\\Users\\Love Yadav\\OneDrive\\Pictures\\Screenshots\\Screenshot 2026-02-11 160050.png");
        emailService.sendEmail("loveyadavrajakataal@gmail.com","Testing email sender","Hello Ji");
    }
}
