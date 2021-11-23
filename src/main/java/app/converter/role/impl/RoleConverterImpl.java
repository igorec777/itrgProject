package app.converter.role.impl;

import app.converter.role.RoleConverter;
import app.dto.role.RoleDto;
import app.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleConverterImpl implements RoleConverter {

    @Override
    public Role fromDto(RoleDto dto) {
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    @Override
    public RoleDto toDto(Role entity) {
        return RoleDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
