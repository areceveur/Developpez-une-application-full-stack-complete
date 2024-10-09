package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<SubscriptionRequest> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUser_Id(userId)
                .stream()
                .map(subscription -> new SubscriptionRequest(
                        subscription.getTheme().getId(),
                        subscription.getUser().getId()
                ))
                .collect(Collectors.toList());
    }
}
