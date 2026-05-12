package org.ncu.EVChargingBooking.exception;

public class StationNotFoundException extends RuntimeException{
	public StationNotFoundException(String message) {
		super(message);
	}
}
