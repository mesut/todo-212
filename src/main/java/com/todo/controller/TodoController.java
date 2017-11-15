package com.todo.controller;

import com.todo.dto.TodoDto;
import com.todo.model.CurrentUser;
import com.todo.model.Todo;
import com.todo.service.TodoService;
import com.todo.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoController {

    private static final String REDIRECT_TODOS = "redirect:/todos/";

    private final TodoService todoService;
    private final Validator validator;

    @Autowired
    public TodoController(TodoService todoService, Validator validator) {
        this.todoService = todoService;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    public String showTodosForDate(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            Model model,
            Authentication auth
    ) {
        CurrentUser user = (CurrentUser) auth.getPrincipal();
        List<Todo> todos = todoService.findTodosByUserIdAndDate(user.getId(), date);
        int progress = todoService.calculateProgress(todos);
        addPageData(date, model, todos, progress);
        addBlankTodo(date, model);
        return "todos";
    }


    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public String showTodosForToday() {
        return REDIRECT_TODOS + DateUtils.getTodayDate();
    }

    @RequestMapping(value = "/{date}/set", method = RequestMethod.GET)
    public String setTodoStatus(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            @RequestParam("id") Long id,
            @RequestParam("status") boolean status
    ) {
        todoService.setTodoStatus(id, status);

        return redirectTodo(date);
    }

    @RequestMapping(value = "/{date}/remove", method = RequestMethod.GET)
    public String removeTodo(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            @RequestParam("id") Long id
    ) {
        todoService.removeTodo(id);

        return redirectTodo(date);
    }


    @RequestMapping(value = "/{date}", method = RequestMethod.POST)
    public String addTodo(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            @Valid TodoDto todoDto,
            BindingResult result,
            RedirectAttributes attr
    ) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.newTodo", result);
            attr.addFlashAttribute("newTodo", todoDto);
            return redirectTodo(date);
        }
        todoService.saveNewTodo(todoDto);

        return redirectTodo(date);
    }

    private void addBlankTodo(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            Model model
    ) {
        if (!model.containsAttribute("newTodo")) {
            TodoDto todoDto = new TodoDto(false, StringUtils.EMPTY, date);
            model.addAttribute("newTodo", todoDto);
        }
    }

    private void addPageData(
            @PathVariable(value = "date") @DateTimeFormat(pattern = DateUtils.yyyyMMdd) LocalDate date,
            Model model,
            List<Todo> todos,
            int progress
    ) {
        model.addAttribute("todos", todos);
        model.addAttribute("progress", progress);
        model.addAttribute("prevDate", date.plusDays(-1));
        model.addAttribute("nowDate", date);
        model.addAttribute("nextDate", date.plusDays(1));
        model.addAttribute("nowMonth", DateUtils.getDateAsString(date));
    }

    private String redirectTodo(LocalDate date) {
        return REDIRECT_TODOS + DateUtils.getDateAsString(date);
    }

}
