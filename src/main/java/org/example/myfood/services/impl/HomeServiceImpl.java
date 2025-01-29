package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;


@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final UserRepository userRepository;

    @Override
    public String home(Model model) {
        return "home";
    }
}
