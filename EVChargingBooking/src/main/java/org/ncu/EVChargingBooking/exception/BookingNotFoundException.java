package org.ncu.EVChargingBooking.exception;

public class BookingNotFoundException extends RuntimeException{
	public BookingNotFoundException(String message) {
		super(message);
	}
}
