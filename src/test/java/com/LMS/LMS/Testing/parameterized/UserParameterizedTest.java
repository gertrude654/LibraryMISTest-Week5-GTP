package com.LMS.LMS.Testing.parameterized;

import com.LMS.LMS.Testing.exception.user.UserNotFoundException;
import com.LMS.LMS.Testing.model.User;
import com.LMS.LMS.Testing.repository.UserRepo;
import com.LMS.LMS.Testing.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserParameterizedTest {

    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "alice, password, true",
            "bob, wrongpassword, false",
            "charlie, password, false"
    })
    void testAuthenticateUser(String username, String password, boolean shouldExist) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        if (shouldExist) {
            when(userRepository.findByUserName(username)).thenReturn(user);
            User authenticatedUser = userService.authenticateUser(username, password);
            assertNotNull(authenticatedUser);
            assertEquals(username, authenticatedUser.getUserName());
        } else {
            when(userRepository.findByUserName(username)).thenReturn(null);
            assertThrows(UserNotFoundException.class, () -> {
                userService.authenticateUser(username, password);
            });
        }
    }
}
