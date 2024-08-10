package com.Ayush.TodoList.service;

import com.Ayush.TodoList.dto.UserDTO;
import com.Ayush.TodoList.model.User;
import com.Ayush.TodoList.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserByUserName(String userName){
        Optional<User> user = userRepository.findByUserName(userName);
        if(user.isEmpty()){
            return new UserDTO();
        }
        else{
            User u = user.get();
            return new UserDTO(u.getUsername(), u.getPassword());
        }
    }
}
