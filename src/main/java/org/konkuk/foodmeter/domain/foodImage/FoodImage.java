package org.konkuk.foodmeter.domain.foodImage;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.konkuk.foodmeter.domain.base.BaseTimeEntity;
import org.konkuk.foodmeter.domain.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @Builder
    public FoodImage(Long id, Grade grade, String imageUrl, User user) {
        this.id = id;
        this.grade = grade;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
