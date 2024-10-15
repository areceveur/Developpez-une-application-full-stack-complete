package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBSubscriptions;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Iterable<DBThemes> getAllThemes() {
        return themeRepository.findAll();
    }

    @Transactional
    public void subscribe(Long userId, Long themeId) {
        Optional<DBUser> userOpt = userRepository.findById(userId);
        Optional<DBThemes> themeOpt = themeRepository.findById(themeId);

        if (userOpt.isPresent() && themeOpt.isPresent()) {
            DBSubscriptions subscription = new DBSubscriptions();
            subscription.setUser(userOpt.get());
            subscription.setTheme(themeOpt.get());
            subscriptionRepository.save(subscription);
        }
    }

    @Transactional
    public void unsubscribe(Long userId, Long themeId) {
        subscriptionRepository.deleteByUser_IdAndTheme_Id(userId, themeId);
    }

    public Iterable<DBThemes> getThemesByIds(List<Long> themesId) {
        return themeRepository.findAllById(themesId);
    }

    public Optional<DBThemes> getThemeById(Long themeId) {
        return themeRepository.findById(themeId);
    }
}
