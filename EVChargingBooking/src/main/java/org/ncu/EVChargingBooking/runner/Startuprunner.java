package org.ncu.EVChargingBooking.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Startuprunner implements CommandLineRunner {

    @Override
    public void run(String... args)
            throws Exception {

        System.out.println(
            "EV Charging Booking System Started Successfully!");
    }
}