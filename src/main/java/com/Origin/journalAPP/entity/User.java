package com.Origin.journalAPP.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @NotBlank
    @Indexed(unique = true)
    private String userName;
    private String email;
    private boolean sentimentAnalysis;

    @NotBlank
    private String password;

    @DBRef(lazy = false)
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private LocalDateTime date;
    private List<String> roles = new ArrayList<>();

}
