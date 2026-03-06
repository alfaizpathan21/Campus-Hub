package com.example.CampusHub.repository;


import com.example.CampusHub.model.GroupMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupMessageRepository
        extends MongoRepository<GroupMessage, String> {

    List<GroupMessage> findByGroupIdOrderByTimestampAsc(String groupId);
}