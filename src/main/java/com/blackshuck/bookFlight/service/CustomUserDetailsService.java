package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.User;
import com.blackshuck.bookFlight.repository.UserRepository;
import com.blackshuck.bookFlight.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find the user"));

        return new CustomUserDetails(user);
    }

}
