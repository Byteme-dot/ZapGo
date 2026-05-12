package org.ncu.EVChargingBooking.dto;

import lombok.Data;

@Data
public class StationAnalyticsDTO {

    private String stationName;
    private int totalBookings;
}