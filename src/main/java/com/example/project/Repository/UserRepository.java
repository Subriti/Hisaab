package com.example.project.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    @Query("SELECT u FROM User u WHERE u.userName=?1")
    Optional<User> findUserByUsername(String username);
    
    //for login
     @Query("SELECT u FROM User u WHERE u.email=?1")
     Optional<User> findUserByEmail(String email);  
}