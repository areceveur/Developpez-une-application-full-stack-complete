package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<DBUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public DBUser saveUser(DBUser dbUser) {
        return userRepository.save(dbUser);
    }

    public Long findUserByEmail(String email) {
        Optional<DBUser> user = userRepository.findByEmail(email);
        return user.map(DBUser::getId).orElseThrow(() -> new RuntimeException("User not found with email" + email));
    }

    public String findUserByUsernameById(Long ownerId) {
       return userRepository.findById(ownerId)
                .map(DBUser::getUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
