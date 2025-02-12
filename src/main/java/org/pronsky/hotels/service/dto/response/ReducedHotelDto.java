package org.pronsky.hotels.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReducedHotelDto {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
