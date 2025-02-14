package org.pronsky.hotels.web.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.pronsky.hotels.data.entities.Address;
import org.pronsky.hotels.service.HotelService;
import org.pronsky.hotels.service.dto.AddressDto;
import org.pronsky.hotels.service.dto.ArrivalTimeDto;
import org.pronsky.hotels.service.dto.ContactsDto;
import org.pronsky.hotels.service.dto.request.HotelForCreatingDto;
import org.pronsky.hotels.service.dto.request.HotelSearchDto;
import org.pronsky.hotels.service.dto.response.FullHotelDto;
import org.pronsky.hotels.service.dto.response.ReducedHotelDto;
import org.pronsky.hotels.service.exceptions.HotelNotFoundException;
import org.pronsky.hotels.web.controller.handler.ExceptionHandlerController;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class HotelControllerImplTest {

    @Mock
    private HotelService hotelService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private HotelControllerImpl hotelController;

    private HotelForCreatingDto hotelToCreateDto;
    private Address address;
    private AddressDto addressDto;
    private ContactsDto contactsDto;
    private ArrivalTimeDto arrivalTimeDto;
    private ReducedHotelDto reducedHotelDto;
    private FullHotelDto fullHotelDto;
    private List<String> amenitiesList;
    private HotelSearchDto hotelSearchDto;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(hotelController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();

        addressDto = AddressDto.builder()
                .houseNumber("9")
                .street("Pobediteley")
                .city("Minsk")
                .country("Belarus")
                .postCode("220004")
                .build();

        contactsDto = ContactsDto.builder()
                .phone("+375 29 1111111")
                .email("someTest_email@mail.com")
                .build();

        arrivalTimeDto = ArrivalTimeDto.builder()
                .checkIn(LocalTime.of(13, 0))
                .checkOut(LocalTime.of(12, 0))
                .build();

        amenitiesList = new ArrayList<>();
        amenitiesList.add("freeParking");
        amenitiesList.add("freeWiFi");
        amenitiesList.add("nonSmokingRooms");
        amenitiesList.add("fitnessCenter");
        amenitiesList.add("petFriendlyRooms");

        address = Address.builder()
                .id(1L)
                .houseNumber("9")
                .street("Pobediteley")
                .city("Minsk")
                .country("Belarus")
                .postCode("220004")
                .build();

        hotelToCreateDto = HotelForCreatingDto.builder()
                .name("Test name")
                .description("Test descr")
                .brand("Test brand")
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTimeDto)
                .build();

        reducedHotelDto = ReducedHotelDto.builder()
                .id(1L)
                .name("Test name")
                .description("Test descr")
                .address(address.toString())
                .phone("+375 29 1111111")
                .build();

        fullHotelDto = FullHotelDto.builder()
                .id(1L)
                .name("Double Tree")
                .description("Test description")
                .brand("Hilton")
                .address(addressDto)
                .contacts(contactsDto)
                .arrivalTime(arrivalTimeDto)
                .amenities(amenitiesList)
                .build();

        hotelSearchDto = HotelSearchDto.builder()
                .name("Test")
                .brand("Hilton")
                .city("Minsk")
                .country("VKL")
                .amenities(amenitiesList)
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @SneakyThrows
    void getAllReduced_ShouldReturnListOfReducedHotelDtos() {
        List<ReducedHotelDto> hotels = List.of(reducedHotelDto);
        String content = objectMapper.writeValueAsString(hotels);
        when(hotelService.findAll()).thenReturn(hotels);

        mockMvc.perform(get("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void getById_ShouldReturnFullHotelDto() {
        when(hotelService.findById(1L)).thenReturn(fullHotelDto);
        String content = objectMapper.writeValueAsString(fullHotelDto);
        mockMvc.perform(get("/property-view/hotels/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void search_ShouldReturnFilteredListOfReducedHotelDtos() {
        List<ReducedHotelDto> hotels = List.of(reducedHotelDto);
        String content = objectMapper.writeValueAsString(hotels);
        when(hotelService.findAllFiltered(any(HotelSearchDto.class))).thenReturn(hotels);

        mockMvc.perform(get("/property-view/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelSearchDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void createHotel_ShouldCreateNewHotelAndReturnDto() {
        when(hotelService.create(hotelToCreateDto)).thenReturn(reducedHotelDto);
        String content = objectMapper.writeValueAsString(reducedHotelDto);
        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelToCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(content))
                .andDo(print());
    }

    @Test
    @SneakyThrows
    void addAmenities_ShouldSetAmenitiesForHotel() {
        long hotelId = 1L;
        mockMvc.perform(post("/property-view/hotels/{id}/amenities", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", String.valueOf(hotelId))
                        .content(objectMapper.writeValueAsString(amenitiesList)))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(hotelService, times(1)).setHotelAmenities(hotelId, amenitiesList);
    }

    @Test
    @SneakyThrows
    void getHistogramByParam_ShouldReturnHistogramData() {
        Map<String, Integer> histogram = new HashMap<>();
        histogram.put("BrandA", 5);
        histogram.put("BrandB", 3);

        when(hotelService.getHistogram("brand")).thenReturn(histogram);

        mockMvc.perform(get("/property-view/histogram/brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.BrandA", is(5)))
                .andExpect(jsonPath("$.BrandB", is(3)));
    }

    @Test
    @SneakyThrows
    void getById_ShouldReturn404IfNotFound() {
        when(hotelService.findById(999L)).thenThrow(new HotelNotFoundException("Hotel not found"));

        mockMvc.perform(get("/property-view/hotels/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createHotel_ShouldReturn400ForInvalidInput() {
        HotelForCreatingDto invalidHotel = HotelForCreatingDto.builder()
                .name("")
                .build();
        when(hotelService.create(invalidHotel)).thenThrow(ConstraintViolationException.class);
        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidHotel)))
                .andExpect(status().isBadRequest());
    }
}
