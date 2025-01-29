package org.example.myfood.services.impl;

import org.example.myfood.DTO.UserDtoAdd;
import org.example.myfood.DTO.UserDtoChangeMacronutrients;
import org.example.myfood.DTO.UserDtoChangePassword;
import org.example.myfood.models.UserModel;
import org.example.myfood.repositories.UserRepository;
import org.example.myfood.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserDtoAdd validUserDto;
    private UserDtoAdd invalidUserDto;

    @BeforeEach
    void setUp() {
        validUserDto = new UserDtoAdd(
                "John", "Doe", "johndoe", "password123", "password123", 2000, 150, 250, 70);
        invalidUserDto = new UserDtoAdd(
                "John", "Doe", "johndoe", "password123", "password321", 2000, 150, 250, 70);
    }

    @Test
    void addUser_shouldAddUserWhenPasswordsMatch() {
        when(passwordEncoder.encode(validUserDto.password())).thenReturn("encodedPassword");

        String result = userServiceImpl.addUser(validUserDto);

        assertEquals("redirect:/login", result);
        verify(userRepository, times(1)).save(any(UserModel.class));
    }

    @Test
    void addUser_shouldReturnRedirectWhenPasswordsDontMatch() {
        String result = userServiceImpl.addUser(invalidUserDto);

        assertEquals("redirect:/user/signUp", result);
        verify(userRepository, times(0)).save(any(UserModel.class));
    }

    @Test
    void login_shouldReturnLoginPage() {
        String result = userServiceImpl.login();
        assertEquals("login", result);
    }

    @Test
    void changeMacronutrients_shouldUpdateMacronutrients() {
        // Prepare mock data
        UserModel user = new UserModel();
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));

        UserDtoChangeMacronutrients dto = new UserDtoChangeMacronutrients(2200, 160, 240, 80);
        String result = userServiceImpl.changeMacronutrients(1L, dto, null);

        assertEquals("redirect:/user/profile", result);
        verify(userRepository, times(1)).save(any(UserModel.class));
    }
}
