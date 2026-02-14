package com.Origin.journalAPP.repository;

import com.Origin.journalAPP.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
//
//    public List<User> getUserForSA(){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
//        return mongoTemplate.find(query, User.class);
//    }

    public List<User> getUserForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("email").exists(true));
        return mongoTemplate.find(query, User.class);
    }
}
