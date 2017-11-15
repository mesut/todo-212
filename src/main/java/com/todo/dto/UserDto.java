package com.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private String username;

    @Size(min = 6, message = "Please provide 6 characters password")
    private String password;

    @Size(min = 6, message = "Please provide 6 characters password")
    private String passwordConfirm;
}
