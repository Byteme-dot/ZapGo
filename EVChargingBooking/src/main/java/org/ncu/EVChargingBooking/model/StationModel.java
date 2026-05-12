package org.ncu.EVChargingBooking.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class StationModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "station_name", length = 25, nullable = false)
	private String stationName;
	@Column(name = "city", length = 20, nullable = false)
	private String city;
	@Column(name = "number_of_ports", nullable = false)
	private int numberOfPorts;
	@OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<BookingModel> bookings;  //Ye represent karta h all bookings from this station
}
