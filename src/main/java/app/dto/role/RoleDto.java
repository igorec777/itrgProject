package app.dto.role;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class RoleDto {

    private final Long id;

    @NotNull
    private final String name;
}
