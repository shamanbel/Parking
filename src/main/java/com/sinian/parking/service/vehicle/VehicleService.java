package com.sinian.parking.service.vehicle;

import com.sinian.parking.entity.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> getAllVehicles();
    Optional<Vehicle> getVehicleById(int id);
    Vehicle createVehicle(Vehicle vehicle);
    Vehicle updateVehicle(int id, Vehicle vehicleDetails);
    void deleteVehicle(int id);
    Vehicle findVehicleByLicensePlate(String licensePlate);
    void updateExitTime(String licensePlate, LocalDateTime exitTime);
}
