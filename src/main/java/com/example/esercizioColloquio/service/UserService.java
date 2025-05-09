package com.example.esercizioColloquio.service;

import com.example.esercizioColloquio.dto.UserDTO;
import com.example.esercizioColloquio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll(){
        return null;
    }

    public UserDTO findById(int id){
        return null;
    }

    public UserDTO create(UserDTO userDTO){
        return null;
    }

    public void update(int id, UserDTO userDTO){

    }

    public void delete(int id){

    }
}
