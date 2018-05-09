package com.kincurrently.repositories;

import com.kincurrently.models.Family;
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

    @Query(value="select * from tasks t where t.title LIKE CONCAT('%',?1,'%') AND designated_user_id IN (SELECT id FROM users WHERE family_id = ?2)", nativeQuery = true)
    public List<Task> findBySearchTerm(String searchTerm, Family family);

    @Query(value="select * from tasks t JOIN tasks_categories tc on t.id = tc.task_id Join categories c ON tc.category_id = c.id WHERE c.name = ?1 AND t.title LIKE CONCAT('%',?2,'%') AND designated_user_id IN (SELECT id FROM users WHERE family_id = ?3)", nativeQuery = true)
    public List<Task> findByCategories(String searchCategory, String searchTerm, Family family);

    @Query(value="SELECT * FROM tasks WHERE designated_user_id IN (SELECT id FROM users WHERE family_id = ?1)", nativeQuery = true)
    List<Task> findTasksByFamily(Family family);
}
