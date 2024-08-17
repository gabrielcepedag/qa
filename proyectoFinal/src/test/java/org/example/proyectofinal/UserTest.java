package org.example.proyectofinal;

import org.example.proyectofinal.cons.Category;
import org.example.proyectofinal.cons.ERole;
import org.example.proyectofinal.dto.request.ProductRequest;
import org.example.proyectofinal.dto.request.UserRequest;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.exception.ResourceNotFoundException;
import org.example.proyectofinal.repository.UserRepository;
import org.example.proyectofinal.service.ProductService;
import org.example.proyectofinal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserTest {
    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private UserRequest userRequest;
    private UserRequest userRequest2;
    private User user;
    private User user2;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userService = new UserService(userRepository, modelMapper);

        userRequest = new UserRequest("Gabriel Cepeda", "gdcg", "1234", "ADMIN");
        userRequest2 = new UserRequest("Eduardo Martinez", "eemr", "5678", "EMPLOYEE");

        user = userService.createUser(userRequest);
        user2 = userService.createUser(userRequest2);
    }

    @Test
    public void testValidUserRequest(){
        UserRequest u1 = new UserRequest("", "username1", "password1", "ADMIN");
        UserRequest u2 = new UserRequest("name", "", "password1", "ADMIN");
        UserRequest u3 = new UserRequest("name", "username2", "", "ADMIN");
        UserRequest u4 = new UserRequest("name", "username3", "password", "ANOTHER");

        assertThrows(BadRequestException.class, () -> userService.createUser(u1));
        assertThrows(BadRequestException.class, () -> userService.createUser(u2));
//        assertThrows(BadRequestException.class, () -> userService.createUser(u3));
        assertThrows(BadRequestException.class, () -> userService.createUser(u4));
    }

    @Test
    public void testAddUser(){
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    public void testFindOneById(){

        User u1 = userService.findOneById(user.getId());
        assertEquals(u1, user);
    }

    @Test
    public void testFindAll(){
        assertEquals(2, userService.findAllUsers().size());
    }

    @Test
    public void testDeleteLogicUserById(){
        boolean bool = userService.deleteUserById(user.getId());

        assertTrue(bool);
        assertEquals(1, userService.findAllUsers().size());
        assertTrue(userRepository.findById(user.getId()).get().isDeleted());
        assertThrows(ResourceNotFoundException.class, () -> userService.findOneById(user.getId()));
    }

    @Test
    public void testUpdateUser() {
       UserRequest updated = new UserRequest("Updated Name", "updated", "updated", "EMPLOYEE");
       User response = userService.updateUser(user.getId(), updated);
       User response2 = userService.findOneById(response.getId());

       assertEquals("Updated Name", response.getName());
       assertEquals("updated", response.getUsername());
       assertEquals("updated", response.getPassword());
       assertEquals("EMPLOYEE", response.getRole().getValue());
       assertEquals(user.getId(), response.getId());
       assertEquals(response2, response);
    }

    @Test
    public void createUserRoleWithDefault(){
        UserRequest ur = new UserRequest();
        ur.setName("name");
        ur.setPassword("password");
        ur.setUsername("username");

        User response = userService.createUser(ur);
        assertEquals(ERole.USER, response.getRole());
    }
}
