package org.example.myfood.services;


import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoChangePassword;
import org.example.myfood.DTO.UserDtoEditProfile;
import org.springframework.ui.Model;

public interface UserService {
    public String addUser(UserDtoAdd userDTO);

    public String login();

    public String profile(Model model);

    public String editProfile(Model model);

    public String editProfile(Long userId, UserDtoEditProfile userDTO);

    public String changePassword(Model model);

    public String changePassword(Long userId, UserDtoChangePassword userDTO, Model model);
}
