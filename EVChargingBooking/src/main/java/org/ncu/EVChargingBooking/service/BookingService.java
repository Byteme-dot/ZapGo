package org.ncu.EVChargingBooking.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.ncu.EVChargingBooking.dao.BookingDao;
import org.ncu.EVChargingBooking.dao.StationDao;
import org.ncu.EVChargingBooking.dto.BookingRequestDTO;
import org.ncu.EVChargingBooking.dto.BookingResponseDTO;
import org.ncu.EVChargingBooking.exception.BookingNotFoundException;
import org.ncu.EVChargingBooking.exception.NoBookingsException;
import org.ncu.EVChargingBooking.exception.SlotAlreadyBookedException;
import org.ncu.EVChargingBooking.exception.StationNotFoundException;
import org.ncu.EVChargingBooking.model.BookingModel;
import org.ncu.EVChargingBooking.model.StationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BookingService {
	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private StationDao stationDao;
	
	public BookingResponseDTO addBooking(BookingRequestDTO booking) {
		
		if(booking.getBookingDateTime() == null) {
		    throw new IllegalArgumentException("Booking date/time is required!!!");
		}
		if(booking.getBookingDateTime().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Date/Time cannot be from the past!!!");
		}
		if(booking.getCustomerName() == null || booking.getCustomerName().isBlank()) {
			throw new IllegalArgumentException("Invalid customer name!!!");
		}
		if(booking.getVehicleNumber() == null || booking.getVehicleNumber().isBlank()) {
			throw new IllegalArgumentException("Invalid vehicle number!!!");
		}
		if(booking.getStationId() <= 0) {
			throw new IllegalArgumentException("Invalid station id");
		}
		
		StationModel stationDetails = stationDao.getStationById(booking.getStationId()); 
		
		BookingModel saveBooking = new BookingModel();
		saveBooking.setBookingDateTime(booking.getBookingDateTime());
		saveBooking.setCustomerName(booking.getCustomerName());
		saveBooking.setStation(stationDetails);
		saveBooking.setVehicleNumber(booking.getVehicleNumber());
		saveBooking.setStatus("BOOKED");
		
		Long bookingCount = bookingDao.countBookingsByStationAndTime(booking.getStationId(),booking.getBookingDateTime());

		if(bookingCount >= stationDetails.getNumberOfPorts()) {
			throw new SlotAlreadyBookedException("All charging ports are occupied for this time slot");
		}
		
		BookingModel returnedBooking = bookingDao.saveBook(saveBooking);
		
 		BookingResponseDTO responseBooking = new BookingResponseDTO();
 		
 		responseBooking.setId(returnedBooking.getId());
		responseBooking.setBookingTime(returnedBooking.getBookingDateTime());
		responseBooking.setCustomerName(returnedBooking.getCustomerName());
		responseBooking.setVehicleNumber(returnedBooking.getVehicleNumber());
		responseBooking.setStatus(returnedBooking.getStatus());
		responseBooking.setStationId(returnedBooking.getStation().getId());
		responseBooking.setStationName(returnedBooking.getStation().getStationName());
		
		return responseBooking;
	}
	
	public List<BookingResponseDTO> getAllBooking() {
		List<BookingModel> allBookings = bookingDao.getAllBookings(); 
		
		if(allBookings.isEmpty()) {
			throw new NoBookingsException("Booking list is empty!!!");
		}
		
		List<BookingResponseDTO> responseList = new ArrayList<>();
		
		for(BookingModel x: allBookings) {
			BookingResponseDTO response = new BookingResponseDTO();
			response.setBookingTime(x.getBookingDateTime());
			response.setId(x.getId());
			response.setCustomerName(x.getCustomerName());
			response.setVehicleNumber(x.getVehicleNumber());
			response.setStatus(x.getStatus());
			response.setStationName(x.getStation().getStationName());
			response.setStationId(x.getStation().getId());
			responseList.add(response);
		}
	
		return responseList;
	}
	
	public BookingResponseDTO getBookingById(int bookingId) {
		BookingModel existingBooking = bookingDao.getBookingById(bookingId);
		
		if(existingBooking == null) {
			throw new BookingNotFoundException("Booking not found!!!");
		}
		
		BookingResponseDTO responseBooking = new BookingResponseDTO();
		responseBooking.setBookingTime(existingBooking.getBookingDateTime());
		responseBooking.setCustomerName(existingBooking.getCustomerName());
		responseBooking.setId(bookingId);
		responseBooking.setVehicleNumber(existingBooking.getVehicleNumber());
		responseBooking.setStationId(existingBooking.getStation().getId());
		responseBooking.setStationName(existingBooking.getStation().getStationName());

		return responseBooking;
	}
	
	public BookingResponseDTO updateBooking(int bookingId, BookingRequestDTO updatedBooking) {
		BookingModel checkBooking = bookingDao.getBookingById(bookingId);
		
		if(checkBooking == null) {
			throw new BookingNotFoundException("Booking not found!!!");
		}
		
		StationModel station = stationDao.getStationById(updatedBooking.getStationId()); //Getting Station details from here
		
		BookingModel updatedBookingModel = new BookingModel();
		updatedBookingModel.setBookingDateTime(updatedBooking.getBookingDateTime());
		updatedBookingModel.setCustomerName(updatedBooking.getCustomerName());
		updatedBookingModel.setVehicleNumber(updatedBooking.getVehicleNumber());
		updatedBookingModel.setStation(station);
				
		BookingModel existingBooking = bookingDao.updateBooking(bookingId, updatedBookingModel);
		
		BookingResponseDTO updatedResponse = new BookingResponseDTO();
		
		updatedResponse.setBookingTime(existingBooking.getBookingDateTime());
		updatedResponse.setCustomerName(existingBooking.getCustomerName());
		updatedResponse.setId(bookingId);
		updatedResponse.setVehicleNumber(existingBooking.getVehicleNumber());
		updatedResponse.setStationId(existingBooking.getStation().getId());
		updatedResponse.setStationName(existingBooking.getStation().getStationName());
		
		return updatedResponse;
	}
	
	public List<BookingResponseDTO> getAllBookingsByStationId(int stationId) {
		StationModel checkStation = stationDao.getStationById(stationId);
		
		if(checkStation == null) {
			throw new StationNotFoundException("Station with ID "+stationId+" not found!!!");
		}
		
		List<BookingModel> returnedList = bookingDao.getAllBookingsByStationId(stationId);
		List<BookingResponseDTO> responseList = new ArrayList<>();
		
		for(BookingModel x: returnedList) {
			BookingResponseDTO response = new BookingResponseDTO();
			
			response.setId(x.getId());
			response.setCustomerName(x.getCustomerName());
			response.setBookingTime(x.getBookingDateTime());
			response.setVehicleNumber(x.getVehicleNumber());
			response.setStationName(x.getStation().getStationName());
			response.setStationId(x.getStation().getId());
			response.setStatus(x.getStatus());
			
			responseList.add(response);
		}
		
		return responseList;
	}
	
	public List<BookingResponseDTO> getAllCompletedBookingsByStationId(int stationId){
		StationModel checkStation = stationDao.getStationById(stationId);
		
		if(checkStation == null) {
			throw new StationNotFoundException("Station with ID"+stationId+" not found!!!");
		}
		
		List<BookingModel> returnedList = bookingDao.getAllCompletedBookingsByStationId(stationId);
		
		if(returnedList.size() <= 0) {
			throw new NoBookingsException("No completed bookings found!!!");
		}
		
		List<BookingResponseDTO> responseList = new ArrayList<>();
		for(BookingModel x: returnedList) {
			BookingResponseDTO response = new BookingResponseDTO();
			
			response.setId(x.getId());
			response.setCustomerName(x.getCustomerName());
			response.setBookingTime(x.getBookingDateTime());
			response.setVehicleNumber(x.getVehicleNumber());
			response.setStationName(x.getStation().getStationName());
			response.setStationId(x.getStation().getId());
			response.setStatus(x.getStatus());
			
			responseList.add(response);
		}
		
		return responseList;
	}
	
	public String completeBooking(int bookingId) {
		BookingModel checkBooking = bookingDao.getBookingById(bookingId);
		
		if(checkBooking == null) {
			throw new BookingNotFoundException("Booking not found!!!");
		}
		
		if(checkBooking.getStatus().equals("CANCELLED")) {
			throw new IllegalArgumentException("Cannot completed a cancelled booking!!!");
		}
		
		return bookingDao.completeBooking(bookingId);
	}
	
	public String cancelBooking(int bookingId) {
		BookingModel checkBooking = bookingDao.getBookingById(bookingId);
		
		if(checkBooking == null) {
			throw new BookingNotFoundException("Booking not found!!!");
		}
		
		return bookingDao.cancelBooking(bookingId);
	}
	
	public String deleteBooking(int bookingId) {
		BookingModel checkBooking = bookingDao.getBookingById(bookingId);
		
		if(checkBooking == null) {
			throw new BookingNotFoundException("Booking not found!!!");
		}
		
		return bookingDao.deleteBooking(bookingId);		
	}
}
