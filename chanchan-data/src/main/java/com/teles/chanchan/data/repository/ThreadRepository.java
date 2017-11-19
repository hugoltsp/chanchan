package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends MongoRepository<com.teles.chanchan.domain.document.Thread, String> {

}
