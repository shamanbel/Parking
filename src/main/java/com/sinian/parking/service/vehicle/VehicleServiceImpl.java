package com.sinian.parking.service.vehicle;

import com.sinian.parking.entity.Vehicle;
import com.sinian.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicleById(int id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    @Override
    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        Vehicle vehicle = vehicleRepository.findVehicleByLicensePlate(licensePlate)
                .orElse(null);
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found with license plate: " + licensePlate);
        }
        return vehicle;

    }
    @Override
    public Vehicle updateVehicle(int id, Vehicle vehicleDetails) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle existingVehicle = optionalVehicle.get();
            // Update data  vehicle
            existingVehicle.setLicensePlate(vehicleDetails.getLicensePlate());
            existingVehicle.setEntryTime(vehicleDetails.getEntryTime());
            existingVehicle.setParkingSpot(vehicleDetails.getParkingSpot());
            existingVehicle.setSubscription(vehicleDetails.isSubscription());
            existingVehicle.setAmountDue(vehicleDetails.getAmountDue());
            existingVehicle.setRate(vehicleDetails.getRate());
            return vehicleRepository.save(existingVehicle);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }

    @Override
    public void deleteVehicle(int id) {
        vehicleRepository.deleteById(id);
    }
    @Override
    public void updateExitTime(String licensePlate, LocalDateTime exitTime) {
        Vehicle vehicle = findVehicleByLicensePlate(licensePlate);
        vehicle.setExitTime(exitTime);
        vehicleRepository.save(vehicle);
    }
}
