package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.dto.requests.SubscriptionRequest;
import com.openclassrooms.mddapi.models.DBSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<DBSubscriptions, Long> {
    List<SubscriptionRequest> findByUser_Id(Long userId);

    void deleteByUser_IdAndTheme_Id(Long userId, Long themeId);
}
