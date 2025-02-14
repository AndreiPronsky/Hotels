package org.pronsky.hotels.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pronsky.hotels.data.entities.Address;
import org.pronsky.hotels.data.entities.Amenities;
import org.pronsky.hotels.data.entities.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelSearchSpecification {

    public static final String FREE_PARKING = "Free parking";
    public static final String FREE_WI_FI = "Free WiFi";
    public static final String NON_SMOKING_ROOMS = "Non-smoking rooms";
    public static final String CONCIERGE = "Concierge";
    public static final String ON_SITE_RESTAURANT = "On-site restaurant";
    public static final String FITNESS_CENTER = "Fitness center";
    public static final String PET_FRIENDLY_ROOMS = "Pet-friendly rooms";
    public static final String ROOM_SERVICE = "Room service";
    public static final String BUSINESS_CENTER = "Business center";
    public static final String MEETING_ROOMS = "Meeting rooms";

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.conjunction();
            }
            String lowerCaseCity = city.toLowerCase();
            Join<Hotel, Address> addressJoin = root.join("address");
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(addressJoin.get("city")),
                    lowerCaseCity
            );
        };
    }

    public static Specification<Hotel> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + name + "%";
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    criteriaBuilder.lower(criteriaBuilder.literal(likePattern))
            );
        };
    }

    public static Specification<Hotel> hasBrand(String brand) {
        return (root, query, criteriaBuilder) -> {
            if (brand == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("brand"), brand);
        };
    }

    public static Specification<Hotel> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null) {
                return criteriaBuilder.conjunction();
            }
            String lowerCaseCountry = country.toLowerCase();
            Join<Hotel, Address> addressJoin = root.join("address");
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(addressJoin.get("country")),
                    lowerCaseCountry
            );
        };
    }

    public static Specification<Hotel> hasAmenities(List<String> amenities) {
        return (root, query, criteriaBuilder) -> {
            if (amenities == null || amenities.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<Hotel, Amenities> amenitiesJoin = root.join("amenities");

            Predicate[] predicates = amenities.stream()
                    .map(amenity -> {
                        switch (amenity) {
                            case FREE_PARKING -> criteriaBuilder.isTrue(amenitiesJoin.get("freeParking"));
                            case FREE_WI_FI -> criteriaBuilder.isTrue(amenitiesJoin.get("freeWiFi"));
                            case NON_SMOKING_ROOMS -> criteriaBuilder.isTrue(amenitiesJoin.get("nonSmokingRooms"));
                            case CONCIERGE -> criteriaBuilder.isTrue(amenitiesJoin.get("concierge"));
                            case ON_SITE_RESTAURANT -> criteriaBuilder.isTrue(amenitiesJoin.get("onSiteRestaurant"));
                            case FITNESS_CENTER -> criteriaBuilder.isTrue(amenitiesJoin.get("fitnessCenter"));
                            case PET_FRIENDLY_ROOMS -> criteriaBuilder.isTrue(amenitiesJoin.get("petFriendlyRooms"));
                            case ROOM_SERVICE -> criteriaBuilder.isTrue(amenitiesJoin.get("roomService"));
                            case BUSINESS_CENTER -> criteriaBuilder.isTrue(amenitiesJoin.get("businessCenter"));
                            case MEETING_ROOMS -> criteriaBuilder.isTrue(amenitiesJoin.get("meetingRooms"));
                            default -> criteriaBuilder.conjunction();
                        }
                        return null;
                    })
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        };
    }
}
