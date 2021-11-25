package app.converter.role;

import app.dto.role.RoleDto;
import app.entity.Role;

public interface RoleConverter {

    RoleDto toRoleDto(Role entity);

    Role fromRoleDto(RoleDto dto);
}
