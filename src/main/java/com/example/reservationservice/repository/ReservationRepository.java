package com.example.reservationservice.repository;

import com.example.reservationservice.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Integer> {

  Optional<ReservationEntity> findByUuid(String reservationUuid);

  Optional<ReservationEntity> deleteByUuid(String reservationUuid);

  Optional<ReservationEntity> findAllByRoomId(BigInteger roomId);

  @Query(value = "select room_id from reservation.reservation r where (:start, :end) overlaps (r.start_date, r.end_date) AND r.room_id = :roomId", nativeQuery = true)
  BigInteger findStartDate(@Param("start") Instant start, @Param("end") Instant end, @Param("roomId") BigInteger roomId);

  List<ReservationEntity> findAllByGuestUuid(String guestUuid);
}
