package com.sinian.parking.entity;

import jakarta.persistence.*;

@Entity
@Table(name="parkingspot")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "spot_number",nullable = false)
    private String spotNumber;

    @Column(name = "available", nullable = false)
    private boolean isAvailable;

    @Column(name = "reservation", nullable = false)
    private boolean isReserved;

      // Each vehicle has only one assigned parking space.
/*      @OneToOne(mappedBy = "parkingSpot", cascade = CascadeType.ALL, orphanRemoval = true)
      private Vehicle vehicle;*/

      @ManyToOne
      @JoinColumn(name = "rate_id")
      private Rate rate;
      public ParkingSpot() {
      }

    public ParkingSpot(Rate rate, Vehicle vehicle, boolean isReserved, boolean isAvailable, String spotNumber, int id) {
        this.rate = rate;
        //this.vehicle = vehicle;
        this.isReserved = isReserved;
        this.isAvailable = isAvailable;
        this.spotNumber = spotNumber;
        this.id = id;
    }



    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public Rate getRate() {return rate;    }
    public void setRate(Rate rate) {
        this.rate = rate;
    }



    public String getSpotNumber() {
        return spotNumber;
    }
    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean isReserved() {
        return isReserved;
    }
    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

/*    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }*/

}
