package com.example.esercizioColloquio.service;

import com.example.esercizioColloquio.dto.NameRequestDTO;
import com.example.esercizioColloquio.dto.UserDTO;
import com.example.esercizioColloquio.entity.User;
import com.example.esercizioColloquio.exception.UserListEmptyException;
import com.example.esercizioColloquio.exception.UserNotFoundException;
import com.example.esercizioColloquio.exception.UserValidationException;
import com.example.esercizioColloquio.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        if(userList.isEmpty()){
            throw new UserListEmptyException("User list is empty");
        }
        for(User user : userList){
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
            response.add(userDTO);
        }
        return response;
    }

    public UserDTO findById(int id){
        int effectiveId = id + 1;
        User user = userRepository.findById(effectiveId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
    }

    public List<UserDTO> findByString(NameRequestDTO request){
        List<User> allUser = userRepository.findAll();
        List<UserDTO> response = new ArrayList<>();

        for(User user : allUser){
            if(user.getName().equals(request.name()) || user.getSurname().equals(request.surname()) || (user.getName().equals(request.name()) && user.getSurname().equals(request.surname()))){
                response.add(new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress()));
            }
        }
        if(response.isEmpty()){
            throw new UserNotFoundException("Impossibile trovare gli utenti cercati");
        }
        return response;
    }

    public UserDTO create(UserDTO request){
        if(request == null){
            throw new UserValidationException("Request cannot be null");
        }
        User user = new User();
        try{
            user.setId(request.id());
            user.setName(request.name());
            user.setSurname(request.surname());
            user.setEmail(request.email());
            user.setAddress(request.address() );
            userRepository.save(user);
        }catch (Exception e){
            logger.error("Error creating user: " + e);
            throw new UserValidationException("Error creating user: " + e.getMessage());
        }
        return new UserDTO(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getAddress());
    }

    public void update(int id, UserDTO request){
        if(request == null) {
            throw new UserValidationException("Request cannot be null");
        }
        User updatedUser = new User();
        try{
            updatedUser.setId(id);
            updatedUser.setName(request.name());
            updatedUser.setSurname(request.surname());
            updatedUser.setEmail(request.email());
            updatedUser.setAddress(request.address());

            userRepository.save(updatedUser);
        } catch (Exception e){
            logger.error("Failed updating user: " + e);
            throw new UserValidationException("Failed updating user: " + e.getMessage());
        }

    }

    public void delete(int id){
        int effectiveId = id + 1;
        if(userRepository.existsById(effectiveId)){
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
