package com.erpmini.erp_core.modules.auth.mapper;


import com.erpmini.erp_core.modules.auth.dto.UserDto;
import com.erpmini.erp_core.modules.auth.entity.*;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserDto toDto(User user);

    @Named("mapRoles")
    static Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) return Set.of();
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}