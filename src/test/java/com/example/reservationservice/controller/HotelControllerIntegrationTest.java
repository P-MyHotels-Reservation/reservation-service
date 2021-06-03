package com.example.reservationservice.controller;

import static org.hamcrest.core.Is.is;
import com.example.reservationservice.model.response.RoomResponse;
import com.example.reservationservice.service.HotelService;
import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "com.example:hotel-service:+:stubs:8090")
public class HotelControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private HotelService hotelService;

  @Test
  public void given_WhenPassEvenNumberInQueryParam_ThenReturnEven()
      throws Exception {

    List<RoomResponse> a= hotelService.getRooms(new BigInteger("1"));
    Assertions.assertNotNull(a);
    Assertions.assertEquals("QUEEN_SUPERIOR_ROOM", a.get(0).getRoomType());
    System.out.println("AHIHI: " + a.get(0).getRoomType());

  }
}
