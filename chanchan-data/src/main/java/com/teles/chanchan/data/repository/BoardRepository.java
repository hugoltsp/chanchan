package com.teles.chanchan.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.orm.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long>{

	List<Board> findAll();
	
	Board findByBoard(String board);
}
