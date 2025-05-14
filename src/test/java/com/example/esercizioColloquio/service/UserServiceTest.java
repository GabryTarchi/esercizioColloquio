package com.example.esercizioColloquio.service;

import com.example.esercizioColloquio.dto.NameRequestDTO;
import com.example.esercizioColloquio.dto.UserDTO;
import com.example.esercizioColloquio.entity.User;
import com.example.esercizioColloquio.exception.*;
import com.example.esercizioColloquio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void findAll_shouldReturnUserDTOList() {
        User user = new User();
        user.setId(1);
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setEmail("mario.example@mail.com");
        user.setAddress("Via Milano 5");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("Mario", result.get(0).name());
    }

    @Test
    void findAll_shouldThrowIfEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(UserListEmptyException.class, () -> userService.findAll());
    }

    @Test
    void findById_shouldReturnUserDTO() {
        User user = new User();
        user.setId(1);
        user.setName("Luca");
        user.setSurname("Rossi");
        user.setEmail("mario.example@mail.com");
        user.setAddress("Via Milano 5");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.findById(1);
        assertEquals("Luca", result.name());
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findById(1));
    }

    @Test
    void create_shouldSaveUser() {
        UserDTO dto = new UserDTO(1, "Anna", "Verdi", "anna@example.com", "Via Firenze 10");
        User user = new User();

        UserService spyService = spy(userService);
        userService.create(dto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_shouldThrowIfNull() {
        assertThrows(UserValidationException.class, () -> userService.create(null));
    }

    @Test
    void delete_shouldRemoveUser() {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.delete(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void delete_shouldThrowIfUserNotExists() {
        when(userRepository.existsById(1)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.delete(1));
    }

    @Test
    void createFromCSV_shouldImportUsers() throws Exception {
        String csv = "name,surname,email,address\nMario,Rossi,mario@example.com,Via Roma 1\n";
        MockMultipartFile file = new MockMultipartFile("file", "users.csv", "text/csv", csv.getBytes());

        List<User> savedUsers = new ArrayList<>();
        when(userRepository.saveAll(anyList())).thenAnswer(invocation -> {
            savedUsers.addAll(invocation.getArgument(0));
            return null;
        });

        List<UserDTO> result = userService.createFromCSV(file);
        assertEquals(1, result.size());
        assertEquals("Mario", result.get(0).name());
    }

    @Test
    void createFromCSV_shouldThrowIfEmptyCSV() {
        MockMultipartFile file = new MockMultipartFile("file", "users.csv", "text/csv", "name,surname,email,address\n".getBytes());
        assertThrows(UserListEmptyException.class, () -> userService.createFromCSV(file));
    }

    @Test
    void findByString_shouldFilterByName() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("Luca");
        user.setSurname("Rossi");
        user.setEmail("mario.example@mail.com");
        user.setAddress("Via Milano 5");
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        NameRequestDTO request = new NameRequestDTO("Luca", null);
        List<UserDTO> result = userService.findByString(request);

        assertEquals(1, result.size());
    }

    @Test
    void findByString_shouldThrowIfRequestNull() {
        assertThrows(RequestEmptyException.class, () -> userService.findByString(null));
    }

    @Test
    void findByString_shouldThrowIfNoMatch() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setEmail("mario.example@mail.com");
        user.setAddress("Via Milano 5");
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        NameRequestDTO request = new NameRequestDTO("Luca", "Verdi");
        assertThrows(UserNotFoundException.class, () -> userService.findByString(request));
    }
}

