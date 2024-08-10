package com.Ayush.TodoList.controller;

import com.Ayush.TodoList.dto.UserDTO;
import com.Ayush.TodoList.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String userName){
        log.info("Call made to - \"GET/api/user/{}\"", userName);
        try{
            UserDTO user = userService.getUserByUserName(userName);
            log.info("User Found");
            return ResponseEntity.ok(user);
        }catch (Exception E){
            System.out.println(E.getMessage());
            log.error(E.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
