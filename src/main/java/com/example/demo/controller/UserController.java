package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<EntityModel <User>> getAllUsers(){
        return userService.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getEmail())).withSelfRel(),
                        linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users")))
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    public String create(@RequestBody User user){
        // System.out.println("Received request body: " + user.getEmail());
        return userService.join(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @GetMapping("/{email}")
    public EntityModel <User> getUser(@PathVariable String email){
        User user = userService.findByEmail(email);
        long id = user.getId();

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(email)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"),
                linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete-user"));

    }
}
