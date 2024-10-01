package org.konkuk.foodmeter.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.konkuk.foodmeter.domain.base.BaseTimeEntity;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    // ImageUrl 추가
    @Builder
    public User(Long id, Provider provider, Role role) {
        this.id = id;
        this.provider = provider;
        this.role = role;
    }
}
