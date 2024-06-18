package com.sinian.parking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ParkingConfig {
    @Value("${parking.capacity}")
    private static int parkingCapacity;

    @Value("${parking.addParkingTimeToExit}")
    private static int addParkingTimeToExit;

    public static int getAddParkingTimeToExit() {
        return addParkingTimeToExit;
    }

    public static int getParkingCapacity() {
        return parkingCapacity;
    }

 /*   public void setParkingCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }*/
}
