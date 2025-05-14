package com.nullifidianz.SimpleCRUD.service;

import com.nullifidianz.SimpleCRUD.controller.DTOs.CreateUserDTO;
import com.nullifidianz.SimpleCRUD.controller.DTOs.UpdateUserDTO;
import com.nullifidianz.SimpleCRUD.entity.User;
import com.nullifidianz.SimpleCRUD.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

        // Transforma DTO para uma Entidade Instanciada

        var entity = new User(
                null,
                createUserDTO.username(),
                createUserDTO.password(),
                createUserDTO.email(),
                Instant.now(),
                null

        );

        // Salva Usando o Metodo do JPA em uma variável pra retornar apenas o

        var savedUser = userRepository.save(entity);
        return savedUser.getId();

    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

    public void updateUser(String id, UpdateUserDTO updateUserDTO){

        var userID = UUID.fromString(id);

        var entity = userRepository.findById(userID);

        if (entity.isPresent()){
            var user = entity.get();
            if(updateUserDTO.username() !=null){
                user.setUsername(updateUserDTO.username());
            }
            if(updateUserDTO.password() != null){
                user.setPassword(updateUserDTO.password());
            }

            userRepository.save(user);
        }



    }

    public void deleteUserbyID (UUID id){
        var existentUser = userRepository.findById(id);
        if (existentUser.isPresent()){
            userRepository.delete(existentUser.get());
        }

    }
}

