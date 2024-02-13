package org.example.myfood.services.impl;

import lombok.AllArgsConstructor;
import org.example.myfood.models.EatenModel;
import org.example.myfood.models.FavoriteModel;
import org.example.myfood.models.ProductModel;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.EatenRepository;
import org.example.myfood.repositories.FavoriteRepository;
import org.example.myfood.repositories.ProductRepository;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.EatenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EatenServiceImpl implements EatenService {
    private UserRepository userRepository;
    private EatenRepository eatenRepository;
    private ProductRepository productRepository;
    private FavoriteRepository favoriteRepository;

    //todo
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


        int totalCalories=0;
        int totalProtein=0;
        int totalCarbohydrate=0;
        int totalFats=0;
        for(EatenModel eatenOne:eaten){
            totalCalories = totalCalories + eatenOne.getProductId().getCalories() * (eatenOne.getQuantity())/100;
            totalProtein = totalProtein + eatenOne.getProductId().getProtein() * (eatenOne.getQuantity())/100;
            totalCarbohydrate = totalCarbohydrate + eatenOne.getProductId().getCarbohydrate() * (eatenOne.getQuantity())/100;
            totalFats = totalFats + eatenOne.getProductId().getFat() * (eatenOne.getQuantity())/100;
        }
        int totalCaloriesPercentage = totalCalories * 100 / user.getDesired_calories();
        int totalProteinPercentage = totalProtein * 100 / user.getDesired_protein();
        int totalCarbohydratePercentage = totalCarbohydrate * 100 / user.getDesired_carbohydrate();
        int totalFatsPercentage = totalFats * 100 / user.getDesired_fat();


        model.addAttribute("eaten", eaten);
        model.addAttribute("totalCalories", totalCalories);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalCarbohydrate", totalCarbohydrate);
        model.addAttribute("totalFats", totalFats);
        model.addAttribute("totalCaloriesPercentage", totalCaloriesPercentage);
        model.addAttribute("totalProteinPercentage", totalProteinPercentage);
        model.addAttribute("totalCarbohydratePercentage", totalCarbohydratePercentage);
        model.addAttribute("totalFatsPercentage", totalFatsPercentage);
        model.addAttribute("date", date);

        return "eaten";
    }

    @Override
    public String eatenDetails(Long eatenId, Model model) {
        LocalDate localDate = LocalDate.now();
        int localDateYear = localDate.getYear();
        int localDateMonth = localDate.getMonthValue();
        int localDateDay = localDate.getDayOfMonth();

        model.addAttribute("");

        if (!eatenRepository.existsById(eatenId)) {
            return "redirect:/eaten/" + localDateDay + "-" + localDateMonth + "-" + localDateYear;
        }

        Optional<EatenModel> product = eatenRepository.findById(eatenId);
        ArrayList<EatenModel> res = new ArrayList<>();
        product.ifPresent(res::add);
        model.addAttribute("product", res);

        return "eatenDetails";
    }


    @Override
    public String likeProduct(Long productId) {
        Long userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = new UserModel();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userRepository.findByUsername(userDetails.getUsername()).get();
        }
        ProductModel product = productRepository.findById(productId).get();

        Long eatenId = 1L;
        if (user != null && product != null) {
            boolean exists = favoriteRepository.existsByUserIdAndProductId(user, product);
            if (!exists) {
                FavoriteModel favorite = new FavoriteModel();
                favorite.setProductId(product);
                favorite.setUserId(user);

                favoriteRepository.save(favorite);

                eatenId = eatenRepository.findByUserIdAndProductId(user, product).getId();
            }
            else {
                eatenId = eatenRepository.findByUserIdAndProductId(user, product).getId();
            }
        }

        return "redirect:/eaten/" + eatenId + "/details";
    }


    @Override
    public String deleteProduct(Long eatenId) {
        LocalDate localDate = LocalDate.now();
        int localDateYear = localDate.getYear();
        int localDateMonth = localDate.getMonthValue();
        int localDateDay = localDate.getDayOfMonth();

        eatenRepository.deleteById(eatenId);

        return "redirect:/eaten/" + localDateDay + "-" + localDateMonth + "-" + localDateYear;
    }
}
