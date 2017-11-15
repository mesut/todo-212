package com.todo.service;

import com.todo.model.CalendarDay;
import com.todo.model.Todo;
import com.todo.util.DateUtils;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtils.class)
public class CalendarServiceTest {

    @InjectMocks
    private CalendarService calendarService;

    @Mock
    private TodoService todoService;

    @Test
    public void shouldGetWeekList() {
        LocalDate date = new LocalDate();
        PowerMockito.mockStatic(DateUtils.class);

        List<List<LocalDate>> listOfDateList = new ArrayList<>();
        List<LocalDate> listOfDate = new ArrayList<>();
        listOfDate.add(date);
        listOfDateList.add(listOfDate);
        when(DateUtils.getWeeksOfTheMonth(date)).thenReturn(listOfDateList);
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo());
        when(todoService.findTodosByUserIdAndDate(1l, date)).thenReturn(todos);
        when(todoService.calculateProgress(todos)).thenReturn(1);

        List<List<CalendarDay>> weeksList = calendarService.getWeeksList(1l, date);

        CalendarDay calendarDay = weeksList.get(0).get(0);
        assertEquals(calendarDay.getDate(), date);
        assertEquals(calendarDay.isBusy(), true);
        assertEquals(calendarDay.getCompleted(), 1);
        assertEquals(calendarDay.isProper(), true);
    }
}