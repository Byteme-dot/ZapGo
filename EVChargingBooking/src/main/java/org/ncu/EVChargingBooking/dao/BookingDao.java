package org.ncu.EVChargingBooking.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.ncu.EVChargingBooking.exception.BookingNotFoundException;
import org.ncu.EVChargingBooking.model.BookingModel;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import jakarta.persistence.EntityManagerFactory;
@Repository
public class BookingDao {
	private final SessionFactory sessionFactory;
	
	public BookingDao(EntityManagerFactory entityManagerFactory) {
		this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	}
	
    public BookingModel saveBook(BookingModel booking) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(booking);
            transaction.commit();
            return booking;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
	
    public List<BookingModel> getAllBookings() {
        Session session = sessionFactory.openSession();

        try {
            List<BookingModel> allBookings = session.createQuery("from BookingModel", BookingModel.class).list();
            return allBookings;
        } finally {
            session.close();
        }
    }
    
    public BookingModel getBookingById(int bookingId) {
    	Session session = sessionFactory.openSession();
    	
    	try {
    		
    		BookingModel existingBooking = session.get(BookingModel.class, bookingId);
    		return existingBooking;
    		
    	}finally {
			session.close();
		}
    }
    
    
    public BookingModel updateBooking(int bookingId, BookingModel updatedBooking) {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
    	try {
    		BookingModel existingBooking = session.get(BookingModel.class, bookingId);
    		
    		existingBooking.setBookingDateTime(updatedBooking.getBookingDateTime());
    		existingBooking.setVehicleNumber(updatedBooking.getVehicleNumber());
    		existingBooking.setCustomerName(updatedBooking.getCustomerName());
    		existingBooking.setStation(updatedBooking.getStation());
    		
    		session.merge(existingBooking);
    		transaction.commit();
    		
    		return session.get(BookingModel.class, bookingId);
    	}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
    }
    
    public String cancelBooking(int bookingId) {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
    	try {
    		BookingModel existingBooking = session.get(BookingModel.class, bookingId);
    		
    		if(existingBooking == null) {
    			throw new BookingNotFoundException("Booking with ID: "+ bookingId+" not found!!!");
    		}
    		
    		existingBooking.setStatus("CANCELLED");
    		
    		session.merge(existingBooking);
    		transaction.commit();
    		
    		return "Booking cancelled successfully!!!";
    	}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
    }
    
    public String deleteBooking(int bookingId) {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
    	try {
    		BookingModel existingBooking = session.get(BookingModel.class, bookingId);
    		
    		session.remove(existingBooking);
    		transaction.commit();
    		
    		return "Booking deleted successfully!!!";
    	}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
    }
    
    public Long countBookingsByStationAndTime(int stationId,LocalDateTime bookingTime) {
    	Session session = sessionFactory.openSession();
    	
    	try {
    		return session.createQuery("select count(*) from BookingModel where "+
    				"station.id = :stationId and bookingDateTime = :bookingTime "+
    				"and status != 'CANCELLED'", Long.class).setParameter("stationId", stationId)
    				.setParameter("bookingTime", bookingTime).uniqueResult();
    	}finally {
			session.close();
		}
    }
    
    public List<BookingModel> getAllBookingsByStationId(int stationId){
    	Session session = sessionFactory.openSession();
    	
    	try {
    		return session.createQuery("from BookingModel where station.id = :stationId", BookingModel.class)
    				.setParameter("stationId", stationId).list();
    	}finally {
    		session.close();
    	}
    }
    
    public String completeBooking(int bookingId) {
    	Session session = sessionFactory.openSession();
    	Transaction transaction = session.beginTransaction();
    	
    	try {
    		BookingModel booking = session.get(BookingModel.class, bookingId);
    		
    		booking.setStatus("COMPLETED");
    		session.merge(booking);
    		transaction.commit();
    		return "Booking completed successfully!!!:";
    	}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
    }
    
    public List<BookingModel> getAllCompletedBookingsByStationId(int stationId){
    	Session session = sessionFactory.openSession();
    	
    	try {
    		return session.createQuery("from BookingModel where station.id = :stationId and status = 'COMPLETED'", BookingModel.class)
    				.setParameter("stationId", stationId).list();
    	}finally {
			session.close();
		}
    }
    
    public Long countActiveBookingsByStationAndTime(
            int stationId,
            LocalDateTime bookingTime) {

        Session session =
                sessionFactory.openSession();

        try {

            return session.createQuery("select count(*) from BookingModel " +
                "where station.id = :stationId and bookingDateTime = :bookingTime and status = 'BOOKED'",Long.class)
            		.setParameter("stationId", stationId).setParameter("bookingTime", bookingTime).uniqueResult();

        }finally {
            session.close();
        }
    }
}
