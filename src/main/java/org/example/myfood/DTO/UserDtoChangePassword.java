package org.example.myfood.DTO;

public record UserDtoChangePassword(
        String oldPassword,
        String password,
        String passwordConfirm
) {
}
