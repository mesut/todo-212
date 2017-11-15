package com.todo.model;

import com.todo.dto.TodoDto;
import com.todo.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TODO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private Long user;

    private boolean status;

    private String todo;

    @DateTimeFormat(pattern = DateUtils.yyyyMMdd)
    private LocalDate date;

    public static Todo createTodo(TodoDto todoDto, CurrentUser user) {
        Todo todo = new Todo();
        todo.setUser(user.getId());
        todo.setDate(todoDto.getDate());
        todo.setStatus(todoDto.isStatus());
        todo.setTodo(todoDto.getTodo());
        return todo;
    }
}
