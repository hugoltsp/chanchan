package com.teles.chanchan.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teles.chanchan.domain.orm.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

}
