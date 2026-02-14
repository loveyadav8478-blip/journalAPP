package com.Origin.journalAPP.service;

import com.Origin.journalAPP.entity.JournalEntry;
import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.repository.JournalEntryRepository;
import com.Origin.journalAPP.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
//import java.util.logging.Logger;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        userRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("An error occurred for {}.",user.getUserName(),e);
            log.info("An error occurred for {}.",user.getUserName(),e);
            log.trace("An error occurred for {}.",user.getUserName(),e);
            log.debug("An error occurred for {}.",user.getUserName(),e);
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId Id){
        return userRepository.findById(Id);
    }

    public void deleteEntryById(ObjectId Id){
        userRepository.deleteById(Id);
    }

    public User findByUsername(String userName){
        return userRepository.findUserByUserName(userName);
    }
//    public void deleteUserByUsername(String username){
//        userRepository.deleteById();
//    }
}
