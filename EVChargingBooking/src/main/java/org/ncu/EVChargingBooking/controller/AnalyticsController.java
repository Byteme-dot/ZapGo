package org.ncu.EVChargingBooking.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.ncu.EVChargingBooking.dto.StationAnalyticsDTO;
import org.ncu.EVChargingBooking.dto.StationOccupancyDTO;
import org.ncu.EVChargingBooking.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Analytics_Controller")
public class AnalyticsController {
	@Autowired
	private AnalyticsService analyticsService;
	
	@GetMapping("/stationusage")
	public List<StationAnalyticsDTO> getStationUsageAnalytics(){
		return analyticsService.getStationUsageAnalytics();
	}
	
	@GetMapping("/topstation")
	public StationAnalyticsDTO getTopStation() {
	    return analyticsService.getTopStation();
	}
	
	@GetMapping("/occupancy/{stationId}")
	public StationOccupancyDTO getStationOccupancy(@PathVariable int stationId, @RequestParam LocalDateTime bookingTime) {
	    return analyticsService.getStationOccupancy(stationId,bookingTime);
	}
}
