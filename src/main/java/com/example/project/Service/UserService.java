package com.example.project.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.User;
import com.example.project.Repository.UserRepository;

import net.minidev.json.JSONObject;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
       }
    
    //shows list of users while adding members to the group
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //register new user
    public JSONObject addNewUser(User user) {
        JSONObject jsonObject = new JSONObject();
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUserName());
        if (userOptional.isPresent()) {
            jsonObject.clear();
            jsonObject.put("Error message", "Username is already taken");
            return jsonObject;
        }

        userRepository.save(user);

        jsonObject.clear();
        jsonObject.put("Success message", "User Successfully Registered !!");
        jsonObject.put("user_id", user.getUserId());
        jsonObject.put("user_name", user.getUserName());
        jsonObject.put("name", user.getName());
        jsonObject.put("email", user.getEmail());
        return jsonObject;
    }

    //find specific user
    public User findUserByID(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));
        return user;
    }

    //login user
    @Transactional
    public User Login(String email, String password) {
        //JSONObject jsonObject = new JSONObject();
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            // If the email is valid --> then login
            String passwordString = userOptional.get().getPassword();
            if (passwordString.equals(password)) {
                User user = userOptional.get();
                /*
                 * jsonObject.put("message", "Successful Login !! ");
                 * jsonObject.put("user_id", user.getUserId());
                 * jsonObject.put("user_name", user.getUserName());
                 * jsonObject.put("name", user.getName());
                 * jsonObject.put("email", user.getEmail());
                 * return jsonObject;
                 */
                return user;
            }
        }
        return null;
        /*
         * jsonObject.put("error", "Email or Password is invalid");
         * return jsonObject;
         */
    }
}
