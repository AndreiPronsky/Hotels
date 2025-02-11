package org.pronsky.hotels.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "amenities")
public class Amenities {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "free_parking", nullable = false)
    private boolean freeParking;

    @Column(name = "free_wifi", nullable = false)
    private boolean freeWiFi;

    @Column(name = "non_smoking_rooms", nullable = false)
    private boolean nonSmokingRooms;

    @Column(name = "concierge", nullable = false)
    private boolean concierge;

    @Column(name = "on_site_restaurant", nullable = false)
    private boolean onSiteRestaurant;

    @Column(name = "fitness_center", nullable = false)
    private boolean fitnessCenter;

    @Column(name = "pet_friendly_rooms", nullable = false)
    private boolean petFriendlyRooms;

    @Column(name = "room_service", nullable = false)
    private boolean roomService;

    @Column(name = "business_center", nullable = false)
    private boolean businessCenter;

    @Column(name = "meeting_rooms", nullable = false)
    private boolean meetingRooms;
}
