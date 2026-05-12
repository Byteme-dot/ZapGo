package org.ncu.EVChargingBooking.exception;

public class SlotAlreadyBookedException extends RuntimeException{
	public SlotAlreadyBookedException(String message) {
		super(message);
	}
}
