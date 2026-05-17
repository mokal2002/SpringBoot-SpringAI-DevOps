package com.mokal.springsecurityapp.utils;

import com.mokal.springsecurityapp.entities.enums.Permission;
import com.mokal.springsecurityapp.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mokal.springsecurityapp.entities.enums.Permission.*;
import static com.mokal.springsecurityapp.entities.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> rolePermissions = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE),
            ADMIN, Set.of(USER_DELETE,USER_CREATE,POST_DELETE,POST_CREATE,USER_UPDATE,POST_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return rolePermissions.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
