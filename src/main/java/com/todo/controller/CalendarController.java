package com.todo.controller;

import com.todo.model.CurrentUser;
import com.todo.service.CalendarService;
import com.todo.util.DateUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value = "/calendar")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(value = "/{year}/{month}", method = RequestMethod.GET)
    public String calendar(@PathVariable("year") int year, @PathVariable("month") int month,
                           Model model, Authentication auth) {
        createPageDate(year,
                model,
                (CurrentUser) auth.getPrincipal(),
                new LocalDate(year, month, 1)
        );

        return "calendar";
    }

    private void createPageDate(@PathVariable("year") int year, Model model, CurrentUser user, LocalDate date) {
        model.addAttribute("weeks", calendarService.getWeeksList(user.getId(), date));
        model.addAttribute("currentDate", new LocalDate());
        model.addAttribute("title", String.format("%d %s", year, date.toString("MMM")));
        model.addAttribute("prevMonth", date.minusMonths(1).toString(DateUtils.yyyyMM));
        model.addAttribute("nextMonth", date.plusMonths(1).toString(DateUtils.yyyyMM));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String currentMonth() {
        LocalDate date = new LocalDate().withDayOfMonth(1);
        return String.format("redirect:/calendar/%s/%s", date.getYear(), date.getMonthOfYear());
    }
}
