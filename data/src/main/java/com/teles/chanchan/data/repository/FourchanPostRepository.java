package com.teles.chanchan.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.fourchan.FourchanPost;

@Repository
public interface FourchanPostRepository extends MongoRepository<FourchanPost, Integer>{

}
