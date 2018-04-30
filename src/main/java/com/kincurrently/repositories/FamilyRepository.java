package com.kincurrently.repositories;

import com.kincurrently.models.Family;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends CrudRepository <Family, Long> {

}
