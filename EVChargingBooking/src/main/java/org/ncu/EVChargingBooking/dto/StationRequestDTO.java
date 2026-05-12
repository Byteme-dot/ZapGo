package org.ncu.EVChargingBooking.dto;

import lombok.Data;

@Data
public class StationRequestDTO {

    private String stationName;
    private String city;
    private int numberOfPorts;
}