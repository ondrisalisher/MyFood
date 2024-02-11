package org.example.myfood.services.impl;

import org.example.myfood.services.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;

@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public String home(Model model) {
        return "home";
    }
}
