package com.konekt.backend.Services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.konekt.backend.Entities.User;
import com.konekt.backend.Repositories.UserRepository;

@Service
public class JPAUserDeteailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("El email %s no pertenece a alguna cuenta", email));
        }
        User user = userOptional.orElseThrow();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getActive(), true, true, true,authorities);
    }

}
