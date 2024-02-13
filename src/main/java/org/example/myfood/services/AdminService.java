package org.example.myfood.services;

import org.springframework.ui.Model;

public interface AdminService {
    public String adminPanel();

    public String users(Model model);

    public String userDetails(Long userId, Model model);

    public String deleteUser(Long userId);
}
