package com.nullifidianz.SimpleCRUD.controller;

import com.nullifidianz.SimpleCRUD.controller.DTOs.CreateUserDTO;
import com.nullifidianz.SimpleCRUD.controller.DTOs.UpdateUserDTO;
import com.nullifidianz.SimpleCRUD.entity.User;
import com.nullifidianz.SimpleCRUD.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// anotação para definir que é um controller e poder fazer as rotas
@RestController
// mapeando a rota de requisicao
@RequestMapping("/v1/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //cria novo user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        var userID = userService.createUser(createUserDTO);

        return ResponseEntity.created(URI.create("v1/users/" + userID.toString())).build();

    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUser(@PathVariable("userID") String userID) {
        var response = userService.findUserById(UUID.fromString(userID));

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity <List<User>> getAllUsers(){
        var response = userService.getAllUsers();

        return ResponseEntity.ok(response);
    }

    @PutMapping("{/userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDTO updateUserDTO){
        userService.updateUser(userId, updateUserDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userID") String userID){
        userService.deleteUserbyID(UUID.fromString(userID));
        return ResponseEntity.noContent().build();
    }

}
