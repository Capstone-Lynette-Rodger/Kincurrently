package com.kincurrently.repositories;

import com.kincurrently.models.User;
import com.kincurrently.models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Roles extends CrudRepository<UserRole, Long> {
    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    List<String> ofUserWith(String username);

    @Query(value = "Select * From user_roles where role = ?1", nativeQuery = true)
    List<UserRole> findByRole(String role);
}
