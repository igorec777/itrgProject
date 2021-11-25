package app.dto.service;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ServiceDto {
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final Double price;
}
