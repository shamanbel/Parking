package com.sinian.parking.repository;

import com.sinian.parking.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
    List<ParkingSpot> findByIsAvailableTrue();

   //  List<ParkingSpot> findByAvailable(boolean isAvailable);
}
