package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.user.User;
import com.springboot.dietapplication.repository.mongo.MongoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private MongoUserRepository mongoUserRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = mongoUserRepository.findByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User with provided name doesn't exist.");
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList
                (new SimpleGrantedAuthority(user.getUserType().name()));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

}
