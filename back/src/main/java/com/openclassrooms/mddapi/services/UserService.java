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
}
