package app.converter.role;

import app.dto.role.RoleDto;
import app.entity.Role;

public interface RoleConverter {

    default RoleDto toDto(Role entity) {
        return null;
    }

    default Role fromDto(RoleDto dto) {
        return null;
    }
}
