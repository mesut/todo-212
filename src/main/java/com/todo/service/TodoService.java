package com.todo.service;

import com.todo.dto.TodoDto;
import com.todo.model.CurrentUser;
import com.todo.model.Todo;
import com.todo.repository.TodoRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public void saveNewTodo(TodoDto todoDto) {
        todoRepository.save(createNewTodo(todoDto));
    }

    public List<Todo> findTodosByUserIdAndDate(Long user, LocalDate date) {
        return todoRepository.findByUserAndDate(user, date);
    }

    public void setTodoStatus(Long id, boolean status) {
        todoRepository.setTodoStatus(status, id);
    }

    public void removeTodo(Long id) {
        todoRepository.delete(id);
    }

    public int calculateProgress(List<Todo> todos) {
        return (int) (100.0 * completedTodoCount(todos) /
                (todos.size() > 0 ? todos.size() : 1));

    }

    private int completedTodoCount(List<Todo> todos) {
        int completed = 0;
        for (Todo todo : todos) {
            if (todo.isStatus()) {
                ++completed;
            }
        }
        return completed;
    }

    private Todo createNewTodo(TodoDto todoDto) {
        CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Todo.createTodo(todoDto, user);
    }
}
