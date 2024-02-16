package org.example.myfood.services;

import org.example.myfood.DTO.AdminDtoUsers;
import org.example.myfood.DTO.UserDtoChangeRole;
import org.springframework.ui.Model;

public interface AdminService {
    public String adminPanel();

    public String users(Model model, AdminDtoUsers adminDtoUsers);

    public String userDetails(Long userId, Model model);

    public String deleteUser(Long userId);

    public String changeRole(Long userId, UserDtoChangeRole userDto);
}
