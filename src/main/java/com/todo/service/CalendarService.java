package com.todo.service;

import com.todo.model.CalendarDay;
import com.todo.model.Todo;
import com.todo.util.DateUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    private final TodoService todoService;

    @Autowired
    public CalendarService(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<List<CalendarDay>> getWeeksList(Long user, LocalDate date) {
        List<List<LocalDate>> weeks = DateUtils.getWeeksOfTheMonth(date);
        List<List<CalendarDay>> calendarWeeks = new ArrayList<>();
        weeks.stream().forEach((week) -> {
            List<CalendarDay> calendarWeek = new ArrayList<>();

            week.stream().forEach((day) -> {
                CalendarDay calendarDay = makeCalendarDay(user, day, date.getMonthOfYear());
                calendarWeek.add(calendarDay);
            });

            calendarWeeks.add(calendarWeek);
        });

        return calendarWeeks;
    }

    private CalendarDay makeCalendarDay(Long user, LocalDate date, int month) {
        List<Todo> todos = todoService.findTodosByUserIdAndDate(user, date);
        boolean busy = !todos.isEmpty();
        int completed = todoService.calculateProgress(todos);

        CalendarDay calendarDay = new CalendarDay(date, busy, completed, date.getMonthOfYear() == month);
        return calendarDay;
    }
}
