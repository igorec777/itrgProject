package dto.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

    private final Long id;
    private final String name;
}
