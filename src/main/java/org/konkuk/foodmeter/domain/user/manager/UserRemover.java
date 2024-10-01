package org.konkuk.foodmeter.domain.user.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;

    public void deleteById(final Long userId) {
        userRepository.deleteById(userId);
    }
}
