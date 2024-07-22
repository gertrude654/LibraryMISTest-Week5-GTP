package com.LMS.LMS.Testing.service.implementation;


import com.LMS.LMS.Testing.exception.user.UserNotFoundException;
import com.LMS.LMS.Testing.model.User;
import com.LMS.LMS.Testing.repository.UserRepo;
import com.LMS.LMS.Testing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public User authenticateUser(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new UserNotFoundException("User not found with provided credentials");
        }
        return user;
    }
}
