package com.sinian.parking.controllers;

import com.sinian.parking.ParkingConfig;
import com.sinian.parking.entity.ParkingSpot;
import com.sinian.parking.entity.Vehicle;
import com.sinian.parking.service.parkingSpot.ParkingSpotService;
import com.sinian.parking.service.vehicle.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;


@RestController
@RequestMapping("/exit")
public class ExitController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping("/{licensePlate}")
    public ResponseEntity<String> vehicleExit(@PathVariable String licensePlate) {
        try {
            Vehicle vehicle = vehicleService.findVehicleByLicensePlate(licensePlate);
            if (vehicle == null) {
                return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
}
            // Checking additional departure time
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime exitTimeWithGracePeriod = vehicle.getEntryTime().plusMinutes(ParkingConfig.getAddParkingTimeToExit() );

            if (currentTime.isAfter(exitTimeWithGracePeriod)) {
                return new ResponseEntity<>("Exit time exceeded the grace period.", HttpStatus.BAD_REQUEST);
            }

            // Update status parking spot
                ParkingSpot parkingSpot = vehicle.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotService.updateParkingSpot(parkingSpot.getId(),parkingSpot);

                vehicleService.updateExitTime(licensePlate, currentTime);
            // Deleting a vehicle record
            vehicleService.deleteVehicle(vehicle.getId());

            return new ResponseEntity<>("Vehicle with license plate " + licensePlate + " has exited.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing the exit.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
