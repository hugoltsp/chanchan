package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.response.ThreadResponse;

@Repository
public interface FourchanThreadRepository extends MongoRepository<ThreadResponse, Integer> {

}
