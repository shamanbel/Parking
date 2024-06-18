package com.sinian.parking.controllers;

import com.sinian.parking.entity.ParkingSpot;
import com.sinian.parking.service.parkingSpot.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parkingspots")
public class ParkingSpotController {
    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpots() {
        List<ParkingSpot> spots = parkingSpotService.getAllParkingSpots();
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpot> getParkingSpotById(@PathVariable int id) {
        ParkingSpot spot = parkingSpotService.getParkingSpotById(id);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParkingSpot> createParkingSpot(@RequestBody ParkingSpot parkingSpot) {
        ParkingSpot createdSpot = parkingSpotService.createParkingSpot(parkingSpot);
        return new ResponseEntity<>(createdSpot, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpot> updateParkingSpot(@PathVariable int id, @RequestBody ParkingSpot parkingSpotDetails) {
        ParkingSpot updatedSpot = parkingSpotService.updateParkingSpot(id, parkingSpotDetails);
        return new ResponseEntity<>(updatedSpot, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingSpot(@PathVariable int id) {
        parkingSpotService.deleteParkingSpot(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpot>> getAvailableParkingSpots() {
        List<ParkingSpot> availableSpots = parkingSpotService.getAvailableParkingSpots();
        return new ResponseEntity<>(availableSpots, HttpStatus.OK);
    }
}
