package converter.role;

import converter.BaseConverter;
import dto.role.RoleDto;
import entity.Role;
import org.springframework.stereotype.Component;

@Component
public class ReadRoleConverterImpl implements BaseConverter<RoleDto, Role> {
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
