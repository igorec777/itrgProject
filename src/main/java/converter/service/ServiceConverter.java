package converter.service;

import dto.service.ServiceDto;
import entity.Service;

public interface ServiceConverter {

    default ServiceDto toDto(Service entity) {
        return null;
    }

    default Service fromDto(ServiceDto dto) {
        return null;
    }
}
