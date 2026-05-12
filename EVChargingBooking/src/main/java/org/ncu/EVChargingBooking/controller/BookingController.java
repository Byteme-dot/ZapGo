package org.ncu.EVChargingBooking.controller;

import java.util.List;

import org.ncu.EVChargingBooking.dto.BookingRequestDTO;
import org.ncu.EVChargingBooking.dto.BookingResponseDTO;
import org.ncu.EVChargingBooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/Booking_Controller")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@GetMapping(value = "/getallbookings")
	public List<BookingResponseDTO> getAllBookingsBookings(){
		return bookingService.getAllBooking();
	}
	
	@GetMapping(value = "/getbookingbyid/{id}")
	public BookingResponseDTO getBookingById(@PathVariable int id) {
		return bookingService.getBookingById(id);
	}
	
	@Operation(summary = "Save a new booking")
	@PostMapping(value = "/savebooking")
	public BookingResponseDTO saveBooking(@RequestBody BookingRequestDTO booking) {
		return bookingService.addBooking(booking);
	}
	
	@PutMapping(value = "/updatebooking/{id}")
	public BookingResponseDTO updateBooking(@PathVariable int id, @RequestBody BookingRequestDTO updatedBooking) {
		return bookingService.updateBooking(id, updatedBooking);
	}
	
	@PutMapping(value = "/cancelbooking/{id}")
	public String cancelBooking(@PathVariable int id) {
		return bookingService.cancelBooking(id);
	}
	
	@DeleteMapping(value = "/deletebooking/{id}")
	public String deleteBooking(@PathVariable int id) {
		return bookingService.deleteBooking(id);
	}
	
	@GetMapping("/getallbookingsbystation/{stationId}")
	public List<BookingResponseDTO>getBookingsByStationId(@PathVariable int stationId) {
		return bookingService.getAllBookingsByStationId(stationId);
	}
	
	@PutMapping("/completebooking/{id}")
	public String completeBooking(@PathVariable int id) {
		return bookingService.completeBooking(id);
	}
	
	@GetMapping("/getallcompletedbookingsbystationid/{id}")
	public List<BookingResponseDTO> getAllCompletedBookingsByStationId(@PathVariable int id){
		return bookingService.getAllCompletedBookingsByStationId(id);
	}
}
