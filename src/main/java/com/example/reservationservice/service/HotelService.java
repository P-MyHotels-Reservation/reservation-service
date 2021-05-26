package com.example.reservationservice.service;

import com.example.reservationservice.model.response.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

@FeignClient(name = "hotelService", url = "${hotel-service.host}", path = "${hotel-service.hotel-path}")
public interface HotelService {

  @GetMapping("/{hotel-id}/rooms/search")
  List<RoomResponse> getRooms(@PathVariable("hotel-id") BigInteger hotelId);
}
