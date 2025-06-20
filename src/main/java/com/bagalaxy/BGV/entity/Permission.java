package com.bagalaxy.BGV.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    // Permission cho Admin
    ADMIN_USER_MANAGE("admin:user:manage"),
    ADMIN_MOVIE_MANAGE("admin:movie:manage"),
    ADMIN_GENRE_MANAGE("admin:genre:manage"),
    ADMIN_COMMENT_MANAGE("admin:comment:manage"),
    ADMIN_RATING_MANAGE("admin:rating:manage"),

    // Permission cho Moderator
    MODERATOR_MOVIE_MANAGE("moderator:movie:manage"),
    MODERATOR_COMMENT_MODERATE("moderator:comment:moderate"),

    // Permission cho User
    USER_WATCH("user:watch"),
    USER_COMMENT("user:comment"),
    USER_FAVORITE("user:favorite"),
    USER_RATING("user:rating"),
    USER_PROFILE("user:profile");

    private final String permission;
}
