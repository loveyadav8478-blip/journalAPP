package com.Origin.journalAPP.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "config_journal_app")
@NoArgsConstructor
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
