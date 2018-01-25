package com.teles.chanchan.web.app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.document.ChanThread;

@Repository
public interface ChanThreadRepository extends MongoRepository<ChanThread, String> {

	Optional<ChanThread> findByNumberAndBoard(int number, String board);

}
