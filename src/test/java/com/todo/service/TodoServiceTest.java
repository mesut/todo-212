package com.todo.service;

import com.todo.dto.TodoDto;
import com.todo.model.CurrentUser;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.repository.TodoRepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityContextHolder.class)
public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    @Test
    public void shouldCreateAndSaveTodo() {
        LocalDate date = new LocalDate();
        CurrentUser currentUser = new CurrentUser(new User(1L, "a", "b"));
        PowerMockito.mockStatic(SecurityContextHolder.class);

        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(currentUser);

        todoService.saveNewTodo(new TodoDto(false, "todo", date));

        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);

        verify(todoRepository).save(captor.capture());

        Todo savedTodo = captor.getValue();
        assertThat(savedTodo.getUser(), equalTo(1l));
        assertThat(savedTodo.getDate(), equalTo(date));
        assertThat(savedTodo.isStatus(), equalTo(false));
        assertThat(savedTodo.getTodo(), equalTo("todo"));
    }

    @Test
    public void shouldFindTodoByUserAndDate() {
        LocalDate date = new LocalDate();
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo());
        when(todoRepository.findByUserAndDate(1l, date)).thenReturn(todos);

        List<Todo> list = todoService.findTodosByUserIdAndDate(1l, date);

        assertEquals(list, todos);
    }

    @Test
    public void shouldSetTodoStatus() {
        todoService.setTodoStatus(1l, true);

        verify(todoRepository).setTodoStatus(true, 1l);
    }

    @Test
    public void shouldRemoveTodo() {
        todoService.removeTodo(1l);
        verify(todoRepository).delete(1l);
    }

    @Test
    public void shouldCalculateProgress() {
        List<Todo> todos = new ArrayList<>();
        Todo todo1 = new Todo(1l, 1l, true, null, null);
        Todo todo2 = new Todo(2l, 1l, false, null, null);
        todos.add(todo1);
        todos.add(todo2);

        assertEquals(todoService.calculateProgress(todos), 50);
    }
}