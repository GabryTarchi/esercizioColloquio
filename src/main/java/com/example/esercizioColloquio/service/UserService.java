package com.example.esercizioColloquio.service;

import com.example.esercizioColloquio.dto.UserDTO;
import com.example.esercizioColloquio.entity.User;
import com.example.esercizioColloquio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll(){
        List<UserDTO> response = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for(User user : userList){
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
            response.add(userDTO);
        }
        return response;
    }

    public UserDTO findById(int id){
        User user = userRepository.findById(id).orElseThrow();
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
    }

    public UserDTO create(UserDTO userDTO){
        User user = new User();

        user.setId(userDTO.id());
        user.setName(userDTO.name());
        user.setSurname(userDTO.surname());
        user.setEmail(userDTO.email());
        user.setAddress(userDTO.address() );
        userRepository.save(user);

        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
    }

    public void update(int id, UserDTO request){
        User updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setName(request.name());
        updatedUser.setSurname(request.surname());
        updatedUser.setEmail(request.email());
        updatedUser.setAddress(request.address());

        userRepository.save(updatedUser);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }
}
