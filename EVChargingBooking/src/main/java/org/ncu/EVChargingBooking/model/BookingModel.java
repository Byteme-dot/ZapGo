package org.ncu.EVChargingBooking.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Data
@Entity
public class BookingModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "booking_date_time", nullable = false)
	private LocalDateTime bookingDateTime;
	@Column(name = "vehicle_number",length = 10, nullable = false)
	private String vehicleNumber;
	@Column(name = "customer_name", length = 30, nullable = false)
	private String customerName;
	@Column(name = "status", length = 10, nullable = false)
	private String status;
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = false)
	private StationModel station;
}
