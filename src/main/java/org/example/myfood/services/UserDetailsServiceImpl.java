package org.example.myfood.services;

import lombok.NoArgsConstructor;
import org.example.myfood.config.UserDetailsImpl;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
