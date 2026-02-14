package com.Origin.journalAPP.repository;

import com.Origin.journalAPP.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findUserByUserName(String userName);

    void deleteByUserName(String userName);
}
