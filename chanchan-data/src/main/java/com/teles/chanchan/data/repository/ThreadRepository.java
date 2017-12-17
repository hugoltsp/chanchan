package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.document.ChanThread;

@Repository
public interface ThreadRepository extends MongoRepository<ChanThread, String> {

}
