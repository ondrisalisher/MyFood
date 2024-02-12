package org.example.myfood.services;


import org.example.myfood.DTO.UserDTO;

public interface UserService {
    public String addUser(UserDTO userDTO);

    public String login();
}
