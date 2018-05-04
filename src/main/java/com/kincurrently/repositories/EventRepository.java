package com.kincurrently.repositories;

import com.kincurrently.models.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public Event findById(Long id);
    public List<Event> findByFamilyId(Long id);

    @Query(value="select * from events e where e.title LIKE CONCAT('%',?1,'%')", nativeQuery = true)
    public List<Event> findBySearchTerm(String searchTerm);
}
