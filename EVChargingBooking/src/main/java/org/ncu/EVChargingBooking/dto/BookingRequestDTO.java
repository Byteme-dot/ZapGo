package org.ncu.EVChargingBooking.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BookingRequestDTO {

    private LocalDateTime bookingDateTime;
    private String customerName;
    private String vehicleNumber;
    private int stationId;
}