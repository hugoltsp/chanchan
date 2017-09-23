package com.teles.chanchan.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<com.teles.chanchan.domain.orm.Thread, Long> {

}
