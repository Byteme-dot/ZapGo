package org.ncu.EVChargingBooking.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgument(IllegalArgumentException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(BookingNotFoundException.class)
	public String handleBookingNotFound(BookingNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(StationNotFoundException.class)
	public String handleStationNotFound(StationNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(NoBookingsException.class)
	public String handleNoBookings(NoBookingsException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(NoStationsException.class)
	public String handleNoStations(NoStationsException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(SlotAlreadyBookedException.class)
	public String handleSlotAlreadyTaken(SlotAlreadyBookedException ex) {
		return ex.getMessage();
	}
}
