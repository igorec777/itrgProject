package converter.role;

import dto.role.RoleDto;
import entity.Role;

public interface RoleConverter {

    default RoleDto toDto(Role entity) {
        return null;
    }

    default Role fromDto(RoleDto dto) {
        return null;
    }
}
