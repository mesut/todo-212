package com.todo.service;

import com.todo.model.CurrentUser;
import com.todo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserService userService;


    @Test
    public void shouldLoadUserByUsername() {

        when(userService.findUserByUsername("mesut")).thenReturn(new User(1l, "aa", "bb"));

        CurrentUser currentUser = userDetailsService.loadUserByUsername("mesut");

        assertEquals(currentUser.getUsername(), "aa");
        assertEquals(currentUser.getPassword(), "bb");


    }
}