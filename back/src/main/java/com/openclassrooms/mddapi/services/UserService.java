package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.requests.UpdateProfileRequest;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

    public void updateUserProfile(UpdateProfileRequest request) {
        Optional<DBUser> userOpt = userRepository.findByEmail(request.getCurrentEmail());

        if (userOpt.isPresent()) {
            DBUser user = userOpt.get();

            if (request.getUsername() != null && !request.getUsername().isEmpty()) {
                user.setUsername(request.getUsername());
            }

            if (request.getNewEmail() != null && !request.getNewEmail().isEmpty()) {
                if (userRepository.findByEmail(request.getNewEmail()).isPresent()) {
                    throw new IllegalArgumentException("L'email " + request.getNewEmail() + " est déjà utilisé");
                }
                user.setEmail(request.getNewEmail());
            }
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Utilisateur non trouvé avec cet email " + request.getCurrentEmail());
        }
    }
}
