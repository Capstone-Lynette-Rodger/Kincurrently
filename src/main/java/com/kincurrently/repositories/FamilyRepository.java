package com.kincurrently.repositories;

import com.kincurrently.models.Event;
import com.kincurrently.models.Family;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends CrudRepository <Family, Long> {
    Family findByCode(String code);

}