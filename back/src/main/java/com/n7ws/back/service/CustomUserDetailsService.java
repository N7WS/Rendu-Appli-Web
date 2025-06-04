package com.n7ws.back.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.n7ws.back.entity.UserEntity;
import com.n7ws.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByEmail(username); //TODO: A changer, ça parle de username pas de UID

        // Vérification que l'on a bien trouvé un utilisateur
        if (user == null) {
            throw new UsernameNotFoundException("User not found with uid: " + username);
        }

        return new User(user.getUid(), user.getPassword(), 
            Collections.singletonList(new SimpleGrantedAuthority(user.getAdmin() ? "ADMIN" : "USER"))
        );
    }


    
}