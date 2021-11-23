package app.converter.service;

import app.dto.service.ServiceDto;
import app.entity.Service;

public interface ServiceConverter {

    default ServiceDto toDto(Service entity) {
        return null;
    }

    default Service fromDto(ServiceDto dto) {
        return null;
    }
}
