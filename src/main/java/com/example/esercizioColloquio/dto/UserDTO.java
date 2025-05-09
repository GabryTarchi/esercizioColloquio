package com.example.esercizioColloquio.dto;

import com.example.esercizioColloquio.entity.User;

public record UserDTO(
        Integer id,
        String name,
        String surname,
        String email,
        String address


) {
    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.id);
        user.setEmail(userDTO.email);
        user.setName(userDTO.name);
        user.setSurname(userDTO.surname);
        user.setAddress(userDTO.address);
        return user;
    }
}
