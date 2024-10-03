package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.DBSubscriptions;
import com.openclassrooms.mddapi.models.DBThemes;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Iterable<DBThemes> getAllThemes() {
        return themeRepository.findAll();
    }

    @Transactional
    public void subscribe(Long userId, Long themeId) {
        Optional<DBUser> userOpt = userRepository.findById(userId);
        Optional<DBThemes> themeOpt = themeRepository.findById(themeId);

//        if (userOpt.isPresent() && themeOpt.isPresent()) {
//            DBUser user = userOpt.get();
//            DBThemes theme = themeOpt.get();
//            user.getSubscriptions().add(theme);
//            userRepository.save(user);
//        }

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
}
