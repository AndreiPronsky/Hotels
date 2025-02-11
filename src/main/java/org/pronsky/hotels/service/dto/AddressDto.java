package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressDto {

    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
