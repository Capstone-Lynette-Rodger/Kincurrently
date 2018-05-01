package com.kincurrently.repositories;

import com.kincurrently.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public List<Event> findById(Long id);
    public List<Event> findByFamilyId(Long id);
}
