package com.kincurrently.repositories;

import com.kincurrently.models.Task;
import com.kincurrently.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
    Task findById(Long id);

    @Query(value="select * from tasks where designated_user_id=?1", nativeQuery = true)
    List<Task> findByDesignatedUser(Long id);

    @Query(value="select * from tasks where creator_id=?1", nativeQuery = true)
    List<Task> findByCreatedUser(Long id);

    @Query(value="select * from tasks t where t.title LIKE CONCAT('%',?1,'%')", nativeQuery = true)
    public List<Task> findBySearchTerm(String searchTerm);
}
