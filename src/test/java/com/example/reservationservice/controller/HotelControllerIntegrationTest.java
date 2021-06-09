package com.example.reservationservice.controller;

import com.example.reservationservice.model.response.RoomResponse;
import com.example.reservationservice.service.HotelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "com.example:hotel-service:+:stubs:8807")
public class HotelControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private HotelService hotelService;

  @Test
  public void shouldReturnRoomList_whenHotelIdIsValid()
      throws Exception {

    List<RoomResponse> hotel= hotelService.getRooms(new BigInteger("1"));
    Assertions.assertNotNull(hotel);
    Assertions.assertNotNull(hotel.get(0).getId());
    Assertions.assertNotNull(hotel.get(0).getHotelId());
    Assertions.assertNotNull(hotel.get(0).getName());
    Assertions.assertNotNull(hotel.get(0).getFloor());
    Assertions.assertNotNull(hotel.get(0).getRoomType());
    Assertions.assertNotNull(hotel.get(0).getPrice());
    Assertions.assertNotNull(hotel.get(0).getComments());
    Assertions.assertNotNull(hotel.get(0).getStatus());
    Assertions.assertEquals("QUEEN_SUPERIOR_ROOM", hotel.get(0).getRoomType());
    Assertions.assertEquals("9", hotel.get(0).getId().toString());
    Assertions.assertEquals("10", hotel.get(1).getId().toString());

  }
}
