package org.ncu.EVChargingBooking.service;

import java.util.ArrayList;
import java.util.List;

import org.ncu.EVChargingBooking.dao.StationDao;
import org.ncu.EVChargingBooking.dto.StationRequestDTO;
import org.ncu.EVChargingBooking.dto.StationResponseDTO;
import org.ncu.EVChargingBooking.exception.NoStationsException;
import org.ncu.EVChargingBooking.exception.StationNotFoundException;
import org.ncu.EVChargingBooking.model.StationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
	@Autowired
	private StationDao stationDao;
	
	public StationResponseDTO saveStation(StationRequestDTO stations) {
		if(stations.getStationName() == null || stations.getStationName().isBlank()){
			throw new IllegalArgumentException("Invalid station name!!!");
		}
		if(stations.getCity() == null || stations.getCity().isBlank()){
			throw new IllegalArgumentException("Invalid city name!!!");
		}
		if(stations.getNumberOfPorts() <= 0) {
			throw new IllegalArgumentException("Invalid number of ports!!!");
		}
		StationModel sendingData = new StationModel();
		
		sendingData.setStationName(stations.getStationName());
		sendingData.setCity(stations.getCity());
		sendingData.setNumberOfPorts(stations.getNumberOfPorts());
		
		StationModel returnedData = stationDao.saveStation(sendingData);
		StationResponseDTO responseData = new StationResponseDTO();
		
		responseData.setId(returnedData.getId());
		responseData.setStationName(returnedData.getStationName());
		responseData.setCity(returnedData.getCity());
		responseData.setNumberOfPorts(returnedData.getNumberOfPorts());
		if(returnedData.getBookings() == null) {
			responseData.setTotalBookings(0);
		}else {
			responseData.setTotalBookings(returnedData.getBookings().size());
		}
		
		return responseData;
	}
	
	public List<StationResponseDTO> getAllStations() {
		List<StationModel> stationList = stationDao.getAllStations();
		
		if(stationList.isEmpty()) {
			throw new NoStationsException("Station List is empty!!!");
		}
		
		List<StationResponseDTO> responseList = new ArrayList<>();
		
		for(StationModel x : stationList) {
			
			StationResponseDTO response = new StationResponseDTO();
			response.setId(x.getId());
			response.setStationName(x.getStationName());
			response.setCity(x.getCity());
			response.setNumberOfPorts(x.getNumberOfPorts());
			response.setTotalBookings(x.getBookings().size());
			
			responseList.add(response);
		}
		
		return responseList;
	}
	
	public StationResponseDTO getStationById(int stationId) {
		StationModel returnedData = stationDao.getStationById(stationId);
		
		if(returnedData == null) {
			throw new StationNotFoundException("Station with ID "+stationId+" not found!!!");
		}
		
		StationResponseDTO responseData = new StationResponseDTO();
		
		responseData.setId(stationId);
		responseData.setCity(returnedData.getCity());
		responseData.setStationName(returnedData.getStationName());
		responseData.setNumberOfPorts(returnedData.getNumberOfPorts());
		responseData.setTotalBookings(returnedData.getBookings().size());
		
		return responseData;
	}
	
	public StationResponseDTO updateStation(int stationId, StationRequestDTO updateStation) {
		StationModel checkStation = stationDao.getStationById(stationId);
		
		if(checkStation == null) {
			throw new StationNotFoundException("Station with ID "+stationId+" not found!!!");
		}
		
		StationModel updatedData = new StationModel();
		updatedData.setId(stationId);
		updatedData.setStationName(updateStation.getStationName());
		updatedData.setCity(updateStation.getCity());
		updatedData.setNumberOfPorts(updateStation.getNumberOfPorts());
		
		StationModel returnedData = stationDao.updateStation(stationId, updatedData);
		
		StationResponseDTO responseData = new StationResponseDTO();
		responseData.setId(stationId);
		responseData.setStationName(returnedData.getStationName());
		responseData.setCity(returnedData.getCity());
		responseData.setNumberOfPorts(returnedData.getNumberOfPorts());
		responseData.setTotalBookings(returnedData.getBookings().size());
		
		return responseData;
	}
	
	public String deleteStation(int stationId) {
		StationModel checkStation = stationDao.getStationById(stationId);
		
		if(checkStation == null) {
			throw new StationNotFoundException("Station with ID "+stationId+" not found!!!");
		}
		
		return stationDao.deleteStation(stationId);
	}
	
	public List<StationResponseDTO> getStationsByCity(String city){
		List<StationModel> stationList = stationDao.getStationsByCity(city);
		
		if(stationList.isEmpty()) {
			throw new NoStationsException("No stations found in this city!!!");
		}
		
		List<StationResponseDTO> responseList = new ArrayList<>();
		
		for(StationModel x: stationList) {
			StationResponseDTO response = new StationResponseDTO();
			
			response.setId(x.getId());
			response.setStationName(x.getStationName());
			response.setCity(x.getCity());
			response.setNumberOfPorts(x.getNumberOfPorts());
			if(x.getBookings() == null) {
				response.setTotalBookings(0);
			}else {
				response.setTotalBookings(x.getBookings().size());
			}
			
			responseList.add(response);
		}
		
		return responseList;
	}
}
