package org.ncu.EVChargingBooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.ncu.EVChargingBooking.dao.BookingDao;
import org.ncu.EVChargingBooking.dao.StationDao;
import org.ncu.EVChargingBooking.dto.StationAnalyticsDTO;
import org.ncu.EVChargingBooking.dto.StationOccupancyDTO;
import org.ncu.EVChargingBooking.exception.StationNotFoundException;
import org.ncu.EVChargingBooking.model.StationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    private StationDao stationDao;
    
    @Autowired
    private BookingDao bookingDao;

    public List<StationAnalyticsDTO> getStationUsageAnalytics() {

        List<StationModel> stations = stationDao.getAllStations();
        List<StationAnalyticsDTO> analyticsList = new ArrayList<>();

        for(StationModel station : stations) {
        	
            StationAnalyticsDTO analytics = new StationAnalyticsDTO();
            analytics.setStationName(station.getStationName());
            
            if(station.getBookings() == null) {
            	analytics.setTotalBookings(0);
            }else{
            	analytics.setTotalBookings(station.getBookings().size());
            }

            analyticsList.add(analytics);
        }

        return analyticsList;
    }
    
    public StationAnalyticsDTO getTopStation() {

        List<StationModel> stations = stationDao.getAllStations();

        StationModel topStation = stations.get(0);

        for(StationModel x : stations) {
        	
        	int currentBookings;
        	if(x.getBookings() == null) {
        		currentBookings = 0;
        	}else {
        		currentBookings = x.getBookings().size();
        	}

            int topBookings;   
            if(topStation.getBookings() == null) {
            	topBookings = 0;
            }else {
            	topBookings = topStation.getBookings().size();
            }

            if(currentBookings > topBookings) {
                topStation = x;
            }
        }

        StationAnalyticsDTO response = new StationAnalyticsDTO();

        response.setStationName(topStation.getStationName());
        if(topStation.getBookings() == null) {        	
        	response.setTotalBookings(0);
        }else {
        	response.setTotalBookings(topStation.getBookings().size());
        }
          
        return response;
    }
    
    public StationOccupancyDTO getStationOccupancy(int stationId,LocalDateTime bookingTime) {

        StationModel station = stationDao.getStationById(stationId);

        if(station == null) {
            throw new StationNotFoundException("Station not found!!!");
        }

        Long occupiedPorts = bookingDao.countActiveBookingsByStationAndTime(stationId,bookingTime);

        StationOccupancyDTO response = new StationOccupancyDTO();

        response.setStationName(station.getStationName());
        response.setTotalPorts(station.getNumberOfPorts());
        response.setOccupiedPorts(occupiedPorts);
        response.setAvailablePorts(station.getNumberOfPorts() - occupiedPorts);

        return response;
    }
}
