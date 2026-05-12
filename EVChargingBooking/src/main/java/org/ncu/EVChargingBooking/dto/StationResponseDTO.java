package org.ncu.EVChargingBooking.dto;

import lombok.Data;

@Data
public class StationResponseDTO {

    private int id;
    private String stationName;
    private String city;
    private int numberOfPorts;
    private int totalBookings;
}