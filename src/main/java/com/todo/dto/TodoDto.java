package com.todo.dto;

import com.todo.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private boolean status;

    @NotNull
    @Size(min = 1, max = 255, message = "Todo description has to be 1-255 characters")
    private String todo;

    @DateTimeFormat(pattern = DateUtils.yyyyMMdd)
    private LocalDate date;
}
