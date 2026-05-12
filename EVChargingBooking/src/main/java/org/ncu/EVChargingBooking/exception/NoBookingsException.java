package org.ncu.EVChargingBooking.exception;

public class NoBookingsException extends RuntimeException{
	public NoBookingsException(String message) {
		super(message);
	}
}
