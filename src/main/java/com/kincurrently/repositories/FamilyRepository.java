package com.kincurrently.repositories;

import com.kincurrently.models.Family;
import org.springframework.data.repository.CrudRepository;

public interface FamilyRepository extends CrudRepository<Family, Long>{
    Family findByCode(String code);
}
