package com.Origin.journalAPP.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String to,String subject,String body){
        try{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);


        }catch(Exception e){
            log.error("Exception while sendEmail " , e);
        }
    }

//    public void sendEmailWithPhoto(String to, String subject, String text, String filePath) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true); // TRUE = multipart (attachment)
//
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text);
//
//            FileSystemResource file = new FileSystemResource(new File(filePath));
//            helper.addAttachment(file.getFilename(), file);
//
//            mailSender.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
