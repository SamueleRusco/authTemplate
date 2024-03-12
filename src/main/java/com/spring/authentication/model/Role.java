package com.spring.authentication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.spring.authentication.model.Permission.*;


@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN( // l'admin puo' fare:
            Set.of(//ritorna un set (lista non indicizzata che non ammette duplicati al suo interno) elementi immutabili (una volta dichiarata la lista non puo' cambiare il suo contenuto)
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(//il manager puo' fare:
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    )

    ;


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
