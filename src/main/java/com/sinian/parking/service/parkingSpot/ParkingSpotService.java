package com.sinian.parking.service.parkingSpot;

import com.sinian.parking.entity.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotService {
    List<ParkingSpot> getAllParkingSpots();
    ParkingSpot getParkingSpotById(int id);
    ParkingSpot createParkingSpot(ParkingSpot parkingSpot);
    ParkingSpot updateParkingSpot(int id, ParkingSpot parkingSpot);
    void deleteParkingSpot(int id);
    int countParkingSpots();
    List<ParkingSpot> getAvailableParkingSpots();
}
