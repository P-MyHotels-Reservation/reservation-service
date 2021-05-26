package com.example.reservationservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation", schema = "reservation")
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;
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
