package com.todo.controller;

import com.todo.model.CalendarDay;
import com.todo.model.CurrentUser;
import com.todo.model.User;
import com.todo.service.CalendarService;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalendarControllerTest {

    @InjectMocks
    private CalendarController calendarController;

    @Mock
    private CalendarService calendarService;

    @Mock
    private TestingAuthenticationToken testingAuthenticationToken;

    @Mock
    private Model model;

    @Test
    public void shouldGetCurrentMonth() throws Exception {
        LocalDate date = new LocalDate().withDayOfMonth(1);

        assertEquals(calendarController.currentMonth(),
                String.format("redirect:/calendar/%d/%d", date.getYear(), date.getMonthOfYear()));

    }

    @Test
    public void shouldGetCalendar() throws Exception {
        CurrentUser currentUser = new CurrentUser(new User(1l, "mesut", "sdfsdf"));
        LocalDate date = new LocalDate(2017, 10, 1);
        List<List<CalendarDay>> weeks = new ArrayList<>();
        CalendarDay calendarDay = new CalendarDay(date, false, 1, false);
        List<CalendarDay> week = new ArrayList<>();
        week.add(calendarDay);
        weeks.add(week);
        when(testingAuthenticationToken.getPrincipal()).thenReturn(currentUser);
        when(calendarService.getWeeksList(1l, date)).thenReturn(weeks);

        String returnVal = calendarController.calendar(2017, 10, model, testingAuthenticationToken);

        assertEquals(returnVal, "calendar");
        verify(model).addAttribute("weeks", weeks);
        verify(model).addAttribute("currentDate", new LocalDate());
        verify(model).addAttribute("title", String.format("%d %s", 2017, date.toString("MMM")));
        verify(model).addAttribute("prevMonth", date.minusMonths(1).toString("yyyy/MM"));
        verify(model).addAttribute("nextMonth", date.plusMonths(1).toString("yyyy/MM"));


    }
}