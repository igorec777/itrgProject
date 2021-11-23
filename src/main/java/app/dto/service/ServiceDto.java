package app.dto.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceDto {
    private final Long id;
    private final String name;
    private final Double price;
}
