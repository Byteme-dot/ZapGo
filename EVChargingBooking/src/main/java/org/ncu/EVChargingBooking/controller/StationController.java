package org.ncu.EVChargingBooking.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.ncu.EVChargingBooking.dto.StationRequestDTO;
import org.ncu.EVChargingBooking.dto.StationResponseDTO;
import org.ncu.EVChargingBooking.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(value = "/Station_Controller")

public class StationController {
	@Autowired
	private StationService stationService;
	
	@GetMapping("/getallstations")
	public List<StationResponseDTO> getallStations() {
		return stationService.getAllStations();
	}
	
	@GetMapping("/getstationbyid/{id}")
	public StationResponseDTO getStationById(@PathVariable int id) {
		return stationService.getStationById(id);
	}
	
	@GetMapping("/getstationbycity/{city}")
	public List<StationResponseDTO> getStationByCity(@PathVariable String city) {
		return stationService.getStationsByCity(city);
	}
	
	@PostMapping("/savestation")
	public StationResponseDTO saveStation(@RequestBody StationRequestDTO stationData) {
		return stationService.saveStation(stationData);
	}
	
	@PutMapping("/updatestation/{id}")
	public StationResponseDTO updateStation(@RequestBody StationRequestDTO stationUpdationData, @PathVariable int id) {
		return stationService.updateStation(id, stationUpdationData);
	}
	
	@DeleteMapping("/deletestation/{id}")
	public String deleteStation(@PathVariable int id) {
		return stationService.deleteStation(id);
	}
	
}
