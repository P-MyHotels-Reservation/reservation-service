package com.example.reservationservice.service;


import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;

import java.util.List;

public interface ReservationService {
  ReservationResponse get(String reservationUuid);

  ReservationResponse create(ReservationCreatedRequest reservationCreatedRequest);

  Boolean delete( String reservationUuid);

  List<ReservationResponse> getReservationList(String guestUuid);

}
