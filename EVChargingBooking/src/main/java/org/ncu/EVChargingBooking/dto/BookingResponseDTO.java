package org.ncu.EVChargingBooking.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingResponseDTO {

    private int id;
    private LocalDateTime bookingTime;
    private String vehicleNumber;
    private String customerName;
    private String status;
    private int stationId;
    private String stationName;
}