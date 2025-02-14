package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class ArrivalTimeDto {

    private LocalTime checkIn;
    private LocalTime checkOut;
}
