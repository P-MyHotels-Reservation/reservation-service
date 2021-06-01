package com.example.reservationservice.controller;

import com.example.reservationservice.model.entity.ReservationEntity;
import com.example.reservationservice.model.request.ReservationCreatedRequest;
import com.example.reservationservice.model.response.ReservationResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reservation")
public interface IReservationController {

  /**
   * This controller API is used to get a single reservation by reservation uuid.
   *
   * @param reservationUuid
   * @return Reservation Response {@link ReservationResponse}
   */
  @GetMapping("/{reservation-uuid}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "get reservation by reservation id",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationEntity.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  ResponseEntity<ReservationResponse> getReservation(@PathVariable("reservation-uuid") String reservationUuid);

  /**
   * This controller API is used to create the reservation
   *
   * @param reservationCreatedRequest {@link ReservationCreatedRequest}
   * @return Reservation Response {@link ReservationResponse}
   */
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "create reservation",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationEntity.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreatedRequest reservationCreatedRequest);

  /**
   * This controller API is used to delete a single reservation with reservation uuid
   *
   * @param reservationUuid Reservation uuid
   * @return Boolean
   */
  @DeleteMapping("/{reservation-uuid}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delete reservation",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Boolean.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  ResponseEntity<Boolean> deleteReservation(@PathVariable("reservation-uuid") String reservationUuid);

  /**
   * This controller API is used to get list of reservations in the database.
   *
   * @return List Reservation Response {@link List<ReservationResponse>}
   */
  @GetMapping("/guest/{guest-uuid}")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get list of reservation by guest uuid",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationEntity.class))}),
      @ApiResponse(responseCode = "404", description = "Not found",
          content = @Content)})
  ResponseEntity<List<ReservationResponse>> getReservationList(@PathVariable("guest-uuid") String guestUuid);
}
