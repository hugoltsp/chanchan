package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.fourchan.FourchanThread;

@Repository
public interface FourchanThreadRepository  extends MongoRepository<FourchanThread, Integer>{

}