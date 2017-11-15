package com.todo.util;


import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public final class DateUtils {

    public final static String yyyyMMdd = "yyyy-MM-dd";
    public final static String yyyyMM = "yyyy/MM";

    private DateUtils() {
    }

    public static List<LocalDate> getDaysBetween(LocalDate date1, LocalDate date2) {
        List<LocalDate> days = new ArrayList<>();

        LocalDate day = date1;
        do {
            days.add(day);
            day = day.plusDays(1);
        } while (day.isBefore(date2));

        return days;
    }

    public static List<LocalDate> getDaysOfTheWeek(LocalDate date) {
        LocalDate date1 = date.withDayOfWeek(1);
        LocalDate date2 = date1.plusWeeks(1);

        return getDaysBetween(date1, date2);
    }

    public static List<List<LocalDate>> getWeeksOfTheMonth(LocalDate date) {
        List<List<LocalDate>> weeks = new ArrayList<>();

        LocalDate cur = date.withDayOfMonth(1).withDayOfWeek(1);
        LocalDate end = date.withDayOfMonth(1).plusMonths(1);

        do {
            List<LocalDate> week = DateUtils.getDaysOfTheWeek(cur);
            weeks.add(week);
            cur = cur.plusWeeks(1);
        } while (cur.isBefore(end));

        return weeks;
    }


    public static String getTodayDate() {
        LocalDate today = new LocalDate();
        return today.toString(yyyyMMdd);
    }

    public static String getDateAsString(LocalDate localDate) {
        return localDate.toString(yyyyMMdd);
    }

}
