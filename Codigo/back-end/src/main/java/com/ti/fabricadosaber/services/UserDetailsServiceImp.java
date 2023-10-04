package com.ti.fabricadosaber.services;



import com.ti.fabricadosaber.models.User;
import com.ti.fabricadosaber.repositories.UserRepository;
import com.ti.fabricadosaber.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(email);

        if (Objects.isNull(user))
            throw new UsernameNotFoundException("User nao encontrado " + email);

        return new UserSpringSecurity(user.getId(), user.getEmail(), user.getPassword(), user.getProfile());
    }

}

// Note que no lugar de username foi usado o email.
