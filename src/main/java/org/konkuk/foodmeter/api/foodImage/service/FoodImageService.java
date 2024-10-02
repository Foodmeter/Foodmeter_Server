package org.konkuk.foodmeter.api.foodImage.service;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.domain.user.User;
import org.konkuk.foodmeter.domain.user.manager.UserRetriever;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodImageService {
    private final UserRetriever userRetriever;

    @Transactional
    public void deleteFoodImage(final Long userId) {
        User user = userRetriever.findById(userId);


    }
}
