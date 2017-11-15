package com.todo.service;


import com.todo.dto.UserDto;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldFindUserByUsername() {
        User user = new User();
        when(userRepository.findUserByUsername("aaa")).thenReturn(user);

        assertEquals(userService.findUserByUsername("aaa"), user);
    }

    @Test
    public void createAndSaveUSer() {
        userService.createAndSave(new UserDto("mesut", "pp", "pp"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals(captor.getValue().getUsername(), "mesut");
    }
}