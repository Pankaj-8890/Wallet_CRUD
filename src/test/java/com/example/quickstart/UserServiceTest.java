package com.example.quickstart;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.UserAlreadyExistsException;
import com.example.quickstart.exceptions.UserNotFoundException;
import com.example.quickstart.models.Country;
import com.example.quickstart.models.UsersModel;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.service.UserService;
import com.example.quickstart.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private WalletService walletService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        openMocks(this);
    }

    @Test
    public void expectUserCreated() throws InvalidAmountException, UserAlreadyExistsException {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new UsersModel("testUser", "encodedPassword", Country.INDIA));
        UsersModel UsersModel = new UsersModel("testUser", "testPassword", Country.INDIA);

        UsersModel savedUser = userService.createUser(UsersModel.getUsername(),UsersModel.getPassword(),UsersModel.getLocation());

        assertEquals("testUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(passwordEncoder, times(1)).encode("testPassword");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void expectUserAlreadyExistsException() throws InvalidAmountException {

        UsersModel UsersModel = new UsersModel("existingUser", "password", Country.INDIA);
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(UsersModel));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(UsersModel.getUsername(),UsersModel.getPassword(),UsersModel.getLocation());
        });
        verify(userRepository, times(1)).findByUsername("existingUser");
        verify(userRepository, never()).save(any());
    }

    @Test
    void expectDeleteUserSuccessfully() throws InvalidAmountException, UserNotFoundException {
        String username = "testUser";
        UsersModel user = new UsersModel(username, "password", Country.INDIA);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String result = userService.deleteUser();

        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).delete(user);
        assertEquals("User " + username + " deleted successfully.", result);
    }

    @Test
    void expectDeleteUserThrowsUserNotFoundException() {
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser());
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, never()).delete(any());
    }





}
