package converter.service;

import converter.BaseConverter;
import dto.service.ServiceDto;
import entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceConverterImpl implements BaseConverter<ServiceDto, Service> {

    @Override
    public Service fromDto(ServiceDto dto) {
        return Service.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public ServiceDto toDto(Service entity) {
        return ServiceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}
