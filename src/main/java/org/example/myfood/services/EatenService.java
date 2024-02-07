package org.example.myfood.services;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface EatenService {
    public String eaten(int day, int month, int year, Model model);
}
