package org.konkuk.foodmeter.domain.user.manager;

import lombok.RequiredArgsConstructor;
import org.konkuk.foodmeter.common.exception.UserException;
import org.konkuk.foodmeter.common.exception.code.UserErrorCode;
import org.konkuk.foodmeter.domain.user.User;
import org.konkuk.foodmeter.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {
    
    private final UserRepository userRepository;

    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_FOUND_USER));
    }
}
