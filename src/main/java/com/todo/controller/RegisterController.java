package com.todo.controller;

import com.todo.dto.UserDto;
import com.todo.service.UserService;
import com.todo.validator.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private static final String REGISTER_PAGE = "register";
    private static final String LOGIN_PAGE = "login";

    private final UserService userService;
    private final UserDtoValidator userDtoValidator;

    @Autowired
    public RegisterController(UserService userService, UserDtoValidator userDtoValidator) {
        this.userService = userService;
        this.userDtoValidator = userDtoValidator;
    }

    @InitBinder("userDto")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userDtoValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return REGISTER_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processRegister(@Valid UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return REGISTER_PAGE;
        }
        userService.createAndSave(userDto);
        return LOGIN_PAGE;
    }

}
