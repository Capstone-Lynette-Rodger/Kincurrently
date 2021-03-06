package com.kincurrently.repositories;

import com.kincurrently.models.Event;
import com.kincurrently.models.Family;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public Event findById(Long id);
    public List<Event> findByFamilyId(Long id);

    @Query(value="select * from events e  where e.title LIKE CONCAT('%',?1,'%') AND e.family_id = ?2", nativeQuery = true)
    public List<Event> findBySearchTerm(String searchTerm, Family family);


    @Query(value= "select * from events e JOIN events_categories ec on e.id = ec.event_id Join categories c ON ec.category_id = c.id where c.name = ?1 AND e.title LIKE CONCAT('%',?2,'%') AND e.family_id = ?3", nativeQuery = true)
    public List<Event> findByCategories(String searchCategory, String searchTerm, Family family);
}
