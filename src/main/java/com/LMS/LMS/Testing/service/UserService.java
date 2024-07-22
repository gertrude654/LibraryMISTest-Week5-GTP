package com.LMS.LMS.Testing.service;

import com.LMS.LMS.Testing.exception.user.UserNotFoundException;
import com.LMS.LMS.Testing.model.User;

public interface UserService {
    User authenticateUser(String username, String password) throws UserNotFoundException;
}
