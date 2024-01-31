package org.example.myfood.services;


public interface UserService {
    public String addUser(String first_name, String last_name, String username, String password, int desired_kkal, int desired_protein, int desired_carbohydrate, int desired_fat);
}
