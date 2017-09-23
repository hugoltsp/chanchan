package com.teles.chanchan.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.orm.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long>{

}
