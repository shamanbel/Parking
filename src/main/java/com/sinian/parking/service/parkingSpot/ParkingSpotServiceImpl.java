package com.sinian.parking.service.parkingSpot;

import com.sinian.parking.entity.ParkingSpot;
import com.sinian.parking.repository.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingSpotServiceImpl implements ParkingSpotService{

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;
    @Override
    public List<ParkingSpot> getAllParkingSpots() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public ParkingSpot getParkingSpotById(int id) {
        return parkingSpotRepository.findById(id).get();
    }

    @Override
    public ParkingSpot createParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public ParkingSpot updateParkingSpot(int id, ParkingSpot parkingSpot) {
        Optional<ParkingSpot> optionalParkingSpot = parkingSpotRepository.findById(id);
        if (optionalParkingSpot.isPresent()) {
            ParkingSpot existingParkingSpot = optionalParkingSpot.get();
            // Update parking place
            existingParkingSpot.setSpotNumber(parkingSpot.getSpotNumber());
            existingParkingSpot.setRate(parkingSpot.getRate());
            return parkingSpotRepository.save(existingParkingSpot);
        } else {
            throw new RuntimeException("Parking Spot not found");
        }
    }
    @Override
    public int countParkingSpots() {
        return (int) parkingSpotRepository.count();
    }
    @Override
    public void deleteParkingSpot(int id) {

    }
    @Override
    public List<ParkingSpot> getAvailableParkingSpots() {
        return parkingSpotRepository.findByIsAvailableTrue();
    }
}
