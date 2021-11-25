package app.converter.service;

import app.dto.service.ServiceDto;
import app.entity.Service;

public interface ServiceConverter {

    ServiceDto toServiceDto(Service entity);

    Service fromServiceDto(ServiceDto dto);
}
