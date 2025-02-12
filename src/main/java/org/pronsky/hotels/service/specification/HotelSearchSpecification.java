package org.pronsky.hotels.service.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pronsky.hotels.data.entities.Amenities;
import org.pronsky.hotels.data.entities.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelSearchSpecification {

    public static Specification<Hotel> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    public static Specification<Hotel> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("name"), name);
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

    public static Specification<Hotel> hasCounty(String county) {
        return (root, query, criteriaBuilder) -> {
            if (county == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("county"), county);
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
                            case "freeParking" -> criteriaBuilder.isTrue(amenitiesJoin.get("freeParking"));
                            case "freeWiFi" -> criteriaBuilder.isTrue(amenitiesJoin.get("freeWiFi"));
                            case "nonSmokingRooms" -> criteriaBuilder.isTrue(amenitiesJoin.get("nonSmokingRooms"));
                            case "concierge" -> criteriaBuilder.isTrue(amenitiesJoin.get("concierge"));
                            case "onSiteRestaurant" -> criteriaBuilder.isTrue(amenitiesJoin.get("onSiteRestaurant"));
                            case "fitnessCenter" -> criteriaBuilder.isTrue(amenitiesJoin.get("fitnessCenter"));
                            case "petFriendlyRooms" -> criteriaBuilder.isTrue(amenitiesJoin.get("petFriendlyRooms"));
                            case "roomService" -> criteriaBuilder.isTrue(amenitiesJoin.get("roomService"));
                            case "businessCenter" -> criteriaBuilder.isTrue(amenitiesJoin.get("businessCenter"));
                            case "meetingRooms" -> criteriaBuilder.isTrue(amenitiesJoin.get("meetingRooms"));
                            default -> criteriaBuilder.conjunction();
                        }
                        return null;
                    })
                    .toArray(Predicate[]::new);

            return criteriaBuilder.and(predicates);
        };
    }
}
