package com.bagalaxy.BGV.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.bagalaxy.BGV.entity.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(
            ADMIN_USER_MANAGE,
            ADMIN_MOVIE_MANAGE,
            ADMIN_GENRE_MANAGE,
            ADMIN_COMMENT_MANAGE,
            ADMIN_RATING_MANAGE
    )),

    MODERATOR(Set.of(
            MODERATOR_MOVIE_MANAGE,
            MODERATOR_COMMENT_MODERATE
    )),

    USER(Set.of(
            USER_WATCH,
            USER_COMMENT,
            USER_FAVORITE,
            USER_RATING,
            USER_PROFILE
    ));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;

    }

    public static Role toEnum(String role) {
        for (Role item : Role.values()) {
            if (item.toString().equalsIgnoreCase(role)) return item;
        }
        return null;
    }
}
