package com.kincurrently.repositories;

import com.kincurrently.models.EventComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCommentRepository extends CrudRepository <EventComment, Long> {
    public List<EventComment> findById(Long id);
}
