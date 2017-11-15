package com.todo.repository;

import com.todo.model.Todo;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUserAndDate(Long author, LocalDate date);

    @Transactional
    @Modifying
    @Query("UPDATE Todo SET status = ? WHERE id = ?")
    int setTodoStatus(boolean status, Long id);
}
