package com.Origin.journalAPP.repository;

import com.Origin.journalAPP.entity.ConfigJournalAppEntity;
import com.Origin.journalAPP.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
