package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.models.EatenModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.EatenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class EatenServiceImpl implements EatenService {
    private UserRepository userRepository;
    private EatenRepository eatenRepository;

    @Override
    public String eaten(int day, int month, int year, Model model) {


        LocalDate date = LocalDate.of(year, month, day);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

            Iterable<EatenModel> eaten = eatenRepository.findByUserIdAndDate(user, date);

        model.addAttribute("eaten", eaten);
        return "eaten";
    }
}
