package org.ncu.EVChargingBooking.dto;

import lombok.Data;

@Data
public class StationOccupancyDTO {

    private String stationName;

    private int totalPorts;

    private long occupiedPorts;

    private long availablePorts;
}