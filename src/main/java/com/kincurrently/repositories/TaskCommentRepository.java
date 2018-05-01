package com.kincurrently.repositories;

import com.kincurrently.models.TaskComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends CrudRepository<TaskComment, Long> {
    List<TaskComment> findByTaskId(Long id);
}
