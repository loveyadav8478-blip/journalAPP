package com.Origin.journalAPP.cache;

import java.util.*;

import com.Origin.journalAPP.entity.ConfigJournalAppEntity;
import com.Origin.journalAPP.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppCache {

    public enum key{
        WEATHER_API
    }

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public Map<String ,String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity e : all) {
            System.out.println("CACHE → " + e.getKey() + " = " + e.getValue());
            APP_CACHE.put(e.getKey(), e.getValue());
        }
    }


}
