package org.konkuk.foodmeter.domain.user.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.domain.user.User;
import org.konkuk.foodmeter.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSaver {

    private final UserRepository userRepository;

    public void save(final User user) {
        userRepository.save(user);
    }
}
