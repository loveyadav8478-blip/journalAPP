package com.Origin.journalAPP.service;

import com.Origin.journalAPP.entity.JournalEntry;
import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.repository.JournalEntryRepository;
import com.Origin.journalAPP.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
//            user.setUserName(null);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured : "+e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId Id){
        return journalEntryRepository.findById(Id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId Id, String userName){
        boolean removed = false;
        try{
            User user = userService.findByUsername(userName);
            removed = user.getJournalEntries().removeIf(e -> e.getId().equals(Id));
            if(removed){
                userService.saveEntry(user);
                journalEntryRepository.deleteById(Id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry "+e);
        }
        return removed;
    }
}
