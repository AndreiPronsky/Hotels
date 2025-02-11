package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContactsDto {

    private String phone;
    private String email;
}
