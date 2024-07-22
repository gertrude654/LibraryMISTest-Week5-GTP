package com.LMS.LMS.Testing.unit;

import com.LMS.LMS.Testing.exception.user.UserNotFoundException;
import com.LMS.LMS.Testing.model.User;
import com.LMS.LMS.Testing.repository.UserRepo;
import com.LMS.LMS.Testing.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class  UserUnitTest  {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser_Success() {
        User user = new User();
        user.setUserName("alice");
        user.setPassword("password");

        when(userRepository.findByUserName("alice")).thenReturn(user);

        User authenticatedUser = userService.authenticateUser("alice", "password");
        assertNotNull(authenticatedUser);
        assertEquals("alice", authenticatedUser.getUserName());
    }

    @Test
    public void testAuthenticateUser_Failure() {
        when(userRepository.findByUserName("alice")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            userService.authenticateUser("alice", "password");
        });
    }
}
