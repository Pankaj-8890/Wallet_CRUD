package com.example.quickstart.config;

import com.example.quickstart.models.UsersModel;
import com.example.quickstart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UsersModel user = this.userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user"));
        return new com.example.quickstart.config.UserDetails(user);
    }

}
