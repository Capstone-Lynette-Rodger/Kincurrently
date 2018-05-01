package com.kincurrently.repositories;

import com.kincurrently.models.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long>{
    Status findByStatus(String status);
}
