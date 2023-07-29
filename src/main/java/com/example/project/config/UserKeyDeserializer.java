package com.example.project.config;

import com.example.project.Model.User;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

public class UserKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String key, DeserializationContext ctxt) {
        // Implement your deserialization logic here
        // For example, you might parse the key as a user ID and return the User object
        // based on the ID from your database or any other source.
        // Alternatively, you might simply return a User object if the key is already in the desired format.
        return new User(key, key, key, key); // Adjust this based on your User class constructor or retrieval logic.
    }
}
