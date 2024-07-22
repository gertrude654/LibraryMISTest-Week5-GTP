package com.LMS.LMS.Testing.integration;

import com.LMS.LMS.Testing.exception.user.UserNotFoundException;
import com.LMS.LMS.Testing.model.User;
import com.LMS.LMS.Testing.repository.UserRepo;
import com.LMS.LMS.Testing.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserIntegrationTest {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testAuthenticateUser_Success() {
        User user = new User();
        user.setUserName("alice");
        user.setPassword("password");
        userRepository.save(user);

        User authenticatedUser = userService.authenticateUser("alice", "password");
        assertNotNull(authenticatedUser);
        assertEquals("alice", authenticatedUser.getUserName());
    }

    @Test
    public void testAuthenticateUser_Failure() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.authenticateUser("alice", "wrongpassword");
        });
    }
}
