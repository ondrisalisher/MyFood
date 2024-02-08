package org.example.myfood.services.impl;

import org.example.myfood.services.HomeService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;

@Service
public class HomeServiceImpl implements HomeService {
    @Override
    public String home(Model model) {
        LocalDate localDate = LocalDate.now();
        int localDateYear = localDate.getYear();
        int localDateMonth = localDate.getMonthValue();
        int localDateDay = localDate.getDayOfMonth();

        model.addAttribute("year", localDateYear);
        model.addAttribute("month", localDateMonth);
        model.addAttribute("day", localDateDay);

        return "home";
    }
}
