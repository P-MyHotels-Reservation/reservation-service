package com.example.reservationservice.model.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.Instant;

@Getter
@Builder
public class ReservationCreatedRequest {
  private String guestUuid;
  private String email;
  private String phone;
  private BigInteger hotelId;
  private String roomType;
  private String status;
  private Instant createdDate;
  private Instant startDate;
  private Instant endDate;
}
