package com.example.esercizioColloquio.controller;

import com.example.esercizioColloquio.dto.NameRequestDTO;
import com.example.esercizioColloquio.dto.UserDTO;
import com.example.esercizioColloquio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    // Metodo di ricerca per nome/cognome o entrambi
    @GetMapping("/name/")
    public ResponseEntity<List<UserDTO>> getUserByString(@RequestBody NameRequestDTO nameRequestDTO){
        return ResponseEntity.ok(userService.findByString(nameRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable int id,
            @RequestBody UserDTO userDTO){
        userService.update(id, userDTO);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
