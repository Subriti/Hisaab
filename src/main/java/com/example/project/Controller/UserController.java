package com.example.project.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.User;
import com.example.project.Service.UserService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/showUsers")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/findUser/{userId}")
    public User findUserByID(@PathVariable int userId) {
        return userService.findUserByID(userId);
    }

    @PostMapping(path = "/loginUser")
    public JSONObject loginUser(@RequestBody Map<String, Object> body) {
        return userService.Login(body.get("email").toString(), body.get("password").toString());
    }

    @PostMapping("/addUser")
    public JSONObject addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }
}
