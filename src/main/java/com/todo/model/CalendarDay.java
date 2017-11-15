package com.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDay {

    private LocalDate date;

    private boolean busy;

    private int completed;

    private boolean proper;
}
