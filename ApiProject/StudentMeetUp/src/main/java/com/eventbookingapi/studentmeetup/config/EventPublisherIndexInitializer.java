package com.eventbookingapi.studentmeetup.config;

import com.eventbookingapi.studentmeetup.collection.Events;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexField;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherIndexInitializer implements ApplicationRunner {
    private final MongoTemplate mongoTemplate;

    public EventPublisherIndexInitializer(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        IndexOperations indexOps = mongoTemplate.indexOps(Events.class);
        boolean hasTextIndex = indexOps.getIndexInfo().stream()
                .flatMap(info -> info.getIndexFields().stream())
                .anyMatch(IndexField::isText);

        if (!hasTextIndex) {
            TextIndexDefinition textIndex = TextIndexDefinition.builder()
                    .named("Events_TextIndex")
                    .onField("publisherId")
                    .onField("title")
                    .onField("type")
                    .onField("location")
                    .build();
            indexOps.ensureIndex(textIndex);
        }
    }
}
