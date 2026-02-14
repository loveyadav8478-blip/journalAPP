package com.Origin.journalAPP.schedular;

import com.Origin.journalAPP.cache.AppCache;
import com.Origin.journalAPP.entity.JournalEntry;
import com.Origin.journalAPP.entity.User;
import com.Origin.journalAPP.enums.Sentiment;
import com.Origin.journalAPP.model.SentimentData;
import com.Origin.journalAPP.repository.UserRepositoryImpl;
import com.Origin.journalAPP.service.EmailService;
import com.Origin.journalAPP.service.SentimateAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimateAnalysisService sentimateAnalysisService;

    @Autowired
    private KafkaTemplate<String,SentimentData> kafkaTemplate;

    @Autowired
    private AppCache appCache;
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaEmails(){
        List<User> users = userRepository.getUserForSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCount = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment != null){
                    sentimentCount.put(sentiment,sentimentCount.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(var entry : sentimentCount.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if(mostFrequentSentiment != null){
                SentimentData sentimentData= SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                try{
                    kafkaTemplate.send("weekly_sentiments", sentimentData.getEmail(), sentimentData);
                } catch (Exception e) {
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }
}
