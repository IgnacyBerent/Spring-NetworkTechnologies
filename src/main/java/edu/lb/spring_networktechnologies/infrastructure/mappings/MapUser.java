package edu.lb.spring_networktechnologies.infrastructure.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.UserEntity;

public class MapUser {
    public static GetUserDto toGetUserDto(UserEntity userEntity) {
        return new GetUserDto(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }
}
