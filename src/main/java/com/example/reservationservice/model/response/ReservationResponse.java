package com.example.reservationservice.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;

@Data
@Builder
public class ReservationResponse {
  private String guestUuid;
  private String uuid;
  private String email;
  private String phone;
  private BigInteger hotelId;
  private BigInteger roomId;
  private String roomType;
  private String status;
  private Instant createdDate;
  private Instant startDate;
  private Instant endDate;
}
