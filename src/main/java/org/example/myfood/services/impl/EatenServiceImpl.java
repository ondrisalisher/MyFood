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

@Service
@AllArgsConstructor
public class EatenServiceImpl implements EatenService {
    private UserRepository userRepository;
    private EatenRepository eatenRepository;

    @Override
    public String eaten(int day, int month, int year, Model model) {
        LocalDate localDate = LocalDate.now();
        int localDateYear = localDate.getYear();
        int localDateMonth = localDate.getMonthValue();
        int localDateDay = localDate.getDayOfMonth();

        LocalDate date = LocalDate.of(year, month, day);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }

        Iterable<EatenModel> eaten = eatenRepository.findByUserIdAndDate(user, date);


        int totalCalories=0;
        int totalProtein=0;
        int totalCarbohydrate=0;
        int totalFats=0;
        for(EatenModel eatenOne:eaten){
            totalCalories = totalCalories + eatenOne.getProductId().getKkal() * (eatenOne.getQuantity())/100;
            totalProtein = totalProtein + eatenOne.getProductId().getProtein() * (eatenOne.getQuantity())/100;
            totalCarbohydrate = totalCarbohydrate + eatenOne.getProductId().getCarbohydrate() * (eatenOne.getQuantity())/100;
            totalFats = totalFats + eatenOne.getProductId().getFat() * (eatenOne.getQuantity())/100;
        }

        model.addAttribute("eaten", eaten);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalCarbohydrate", totalCarbohydrate);
        model.addAttribute("totalFats", totalFats);
        model.addAttribute("date", date);

        model.addAttribute("year", localDateYear);
        model.addAttribute("month", localDateMonth);
        model.addAttribute("day", localDateDay);

        return "eaten";
    }
}
