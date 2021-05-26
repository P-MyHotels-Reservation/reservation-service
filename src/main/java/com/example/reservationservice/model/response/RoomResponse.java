package com.example.reservationservice.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
@Builder
public class RoomResponse {
  private BigInteger id;
  private BigInteger hotelId;
  private String name;
  private String floor;
  private String roomType;
  private String price;
  private String comments;
  private String status;
  private Instant updatedDate;
  private Instant createdDate;
}

