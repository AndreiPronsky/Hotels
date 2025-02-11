package org.pronsky.hotels.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AmenitiesDto {
    private boolean freeParking;
    private boolean freeWiFi;
    private boolean nonSmokingRooms;
    private boolean concierge;
    private boolean onSiteRestaurant;
    private boolean fitnessCenter;
    private boolean petFriendlyRooms;
    private boolean roomService;
    private boolean businessCenter;
    private boolean meetingRooms;
}
