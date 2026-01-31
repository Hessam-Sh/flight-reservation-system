package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.User;
import com.blackshuck.bookFlight.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService
{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addNew(User user)
    {
        if (user.getEmail().equals("hessam.blackshuck@gmail.com"))
        {
            user.setAdmin(true);
        }

        user.setActive(true);

        user.setRegistrationDate(new Date(System.currentTimeMillis()));

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public boolean emailRepetitionError(String email)
    {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String currentName)
    {
        return userRepository.findByEmail(currentName).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
