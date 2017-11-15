package com.todo.controller;

import com.todo.dto.TodoDto;
import com.todo.model.CurrentUser;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.service.TodoService;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Mock
    private Validator validator;

    @Mock
    private Model model;

    @Mock
    private WebDataBinder webDataBinder;

    @Mock
    private Authentication authentication;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;


    @Test
    public void shouldInitBinder() {
        todoController.initBinder(webDataBinder);
        verify(webDataBinder).setValidator(validator);
    }

    @Test
    public void shouldShowTodosForDateAndNoNewTodo() {
        LocalDate date = new LocalDate();
        CurrentUser currentUser = new CurrentUser(new User(1l, "mesut", "pass"));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        ArrayList<Todo> todos = new ArrayList<>();
        when(todoService.findTodosByUserIdAndDate(1l, date)).thenReturn(todos);
        when(todoService.calculateProgress(todos)).thenReturn(1);
        when(model.containsAttribute("newTodo")).thenReturn(false);
        String returnVal = todoController.showTodosForDate(date, model, authentication);

        assertEquals(returnVal, "todos");
        verify(model).addAttribute("todos", todos);
        verify(model).addAttribute("progress", 1);
        verify(model).addAttribute("nowDate", date);
        verify(model).addAttribute("nextDate", date.plusDays(1));
        verify(model).addAttribute(eq("newTodo"), anyObject());
    }

    @Test
    public void shouldShowTodosForDateAWithNewTodo() {
        LocalDate date = new LocalDate();
        CurrentUser currentUser = new CurrentUser(new User(1l, "mesut", "pass"));
        when(authentication.getPrincipal()).thenReturn(currentUser);
        when(model.containsAttribute("newTodo")).thenReturn(true);
        String returnVal = todoController.showTodosForDate(date, model, authentication);

        assertEquals(returnVal, "todos");
        verify(model, times(0)).addAttribute(eq("newTodo"), anyObject());
    }

    @Test
    public void shodTodosForToday() {
        LocalDate date = new LocalDate();

        assertEquals(todoController.showTodosForToday(), "redirect:/todos/" + date.toString("yyyy-MM-dd"));
    }

    @Test
    public void shouldSetTodoStatus() {
        LocalDate date = new LocalDate();
        assertEquals(todoController.setTodoStatus(date, 1L, true), "redirect:/todos/" + date.toString("yyyy-MM-dd"));
        verify(todoService).setTodoStatus(1L, true);
    }

    @Test
    public void shouldRemoveTodoById() {
        LocalDate date = new LocalDate();
        assertEquals(todoController.removeTodo(date, 1L), "redirect:/todos/" + date.toString("yyyy-MM-dd"));
        verify(todoService).removeTodo(1L);
    }

    @Test
    public void shouldAddTodoWhenNoError() {
        LocalDate date = new LocalDate();
        when(bindingResult.hasErrors()).thenReturn(false);
        TodoDto todoDto = new TodoDto(false, "sasa", date);

        String returnVal = todoController.addTodo(date, todoDto, bindingResult, redirectAttributes);

        assertEquals(returnVal, "redirect:/todos/" + date.toString("yyyy-MM-dd"));
        verify(todoService).saveNewTodo(todoDto);
    }
}
