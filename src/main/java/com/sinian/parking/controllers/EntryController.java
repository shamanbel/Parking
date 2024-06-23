package com.sinian.parking.controllers;

import com.sinian.parking.ParkingConfig;
import com.sinian.parking.entity.ParkingSpot;
import com.sinian.parking.entity.Vehicle;
import com.sinian.parking.service.osr.OCRService;
import com.sinian.parking.service.parkingSpot.ParkingSpotService;
import com.sinian.parking.service.vehicle.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entry")
public class EntryController {


        @Autowired
        private VehicleService vehicleService;

        @Autowired
        private ParkingSpotService parkingSpotService;

        @Autowired
        private OCRService ocrService;

        @PostMapping
        public ResponseEntity<String> vehicleEntry(@RequestBody String licensePlate) {

            // Checking the availability of a parking space
            Optional<ParkingSpot> availableParkingSpot = findAvailableParkingSpot();
            if (availableParkingSpot.isPresent()) {
                System.out.println("Available Parking Spot: " + availableParkingSpot.get());
            } else {
                System.out.println("No available parking spots found.");
            }
            if (availableParkingSpot.isPresent() && !checkParkingCapacity()) {
                // Creating a Vehicle object
                Vehicle vehicle = new Vehicle();
                vehicle.setLicensePlate(licensePlate);
                vehicle.setParkingSpot(availableParkingSpot.get());
                vehicle.setEntryTime(LocalDateTime.now());

                // Saving a Vehicle object
                Vehicle savedVehicle = vehicleService.createVehicle(vehicle);

                // Updating the parking spot
                ParkingSpot spot = availableParkingSpot.get();

                spot.setAvailable(false); // Update the availability status to false
                parkingSpotService.updateParkingSpot(spot.getId(), spot);

                return new ResponseEntity<>("Vehicle with license plate " + licensePlate + " has been successfully entered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No available parking spots at the moment.", HttpStatus.BAD_REQUEST);
            }


        }

    private Optional<ParkingSpot> findAvailableParkingSpot() {
        // Getting all parking spaces
        List<ParkingSpot> allParkingSpots = parkingSpotService.getAllParkingSpots();

        // Checking the availability of a parking space, taking into account reservations and occupancy
        for (ParkingSpot parkingSpot : allParkingSpots) {
            if (parkingSpot.isAvailable() && !parkingSpot.isReserved()) {
                return Optional.of(parkingSpot);
            }
        }

        return Optional.empty();
    }

    private boolean checkParkingCapacity() {
        int totalSpots = ParkingConfig.getParkingCapacity();
        int occupiedSpots = (int) parkingSpotService.getAllParkingSpots().stream().filter(ParkingSpot::isAvailable).count();
        return occupiedSpots < totalSpots;
    }
}
   /* @PostMapping
    public ResponseEntity<String> vehicleEntry(@RequestParam("image") MultipartFile image) {
        try {
            // License plate recognition
            File imageFile = convertMultipartFileToFile(image);
            String licensePlate = ocrService.recognizeLicensePlate(imageFile);

            // Checking the availability of a parking space
            Optional<ParkingSpot> availableParkingSpot = findAvailableParkingSpot();
            if (availableParkingSpot.isPresent() && checkParkingCapacity()) {
                // Creating a Vehicle object
                Vehicle vehicle = new Vehicle();
                vehicle.setLicensePlate(licensePlate);
                vehicle.setParkingSpot(availableParkingSpot.get());
                vehicle.setEntryTime(LocalDateTime.now());

                // Saving a Vehicle object
                Vehicle savedVehicle = vehicleService.createVehicle(vehicle);

                // Saving a Vehicle object
                ParkingSpot spot = availableParkingSpot.get();
                spot.setAvailable(true);
                parkingSpotService.updateParkingSpot(spot.getId(), spot);

                return new ResponseEntity<>("Vehicle with license plate " + licensePlate + " has been successfully entered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No available parking spots at the moment.", HttpStatus.BAD_REQUEST);
            }
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

 /*   private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }*/

/*    private Optional<ParkingSpot> findAvailableParkingSpot() {
        // Getting all parking spaces
        List<ParkingSpot> allParkingSpots = parkingSpotService.getAllParkingSpots();

        // Checking the availability of a parking space, taking into account reservations and occupancy
        for (ParkingSpot parkingSpot : allParkingSpots) {
            if (!parkingSpot.isAvailable() && !parkingSpot.isReserved()) {
                return Optional.of(parkingSpot);
            }
        }

        return Optional.empty();
    }

    private boolean checkParkingCapacity() {
        int totalSpots = parkingConfig.getParkingCapacity();
        int occupiedSpots = (int) parkingSpotService.getAllParkingSpots().stream().filter(ParkingSpot::isAvailable).count();
        return occupiedSpots < totalSpots;
    }*/

