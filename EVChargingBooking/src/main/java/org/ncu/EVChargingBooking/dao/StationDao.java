package org.ncu.EVChargingBooking.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.ncu.EVChargingBooking.model.StationModel;
import jakarta.persistence.EntityManagerFactory;

@Repository
public class StationDao{

	private final SessionFactory sessionFactory;
	
	public StationDao(EntityManagerFactory entityManagerFactory) {
		this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	public StationModel saveStation(StationModel station) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			session.persist(station);
			transaction.commit();
			return station;
		}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
	
	public List<StationModel> getAllStations() {
		Session session = sessionFactory.openSession();
		
		try {
			List<StationModel> allStations = session.createQuery("from StationModel", StationModel.class).list();
			return allStations;
		}finally {
			session.close();
		}
	}
	
	public StationModel getStationById(int stationId) {
		Session session = sessionFactory.openSession();
		
		try {
			StationModel station = session.get(StationModel.class, stationId);
			return station;
		}finally {
			session.close();
		}
	}
	
	public StationModel updateStation(int stationId, StationModel updatedStation) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			StationModel existingStation = session.get(StationModel.class, stationId);
			
			existingStation.setStationName(updatedStation.getStationName());
			existingStation.setCity(updatedStation.getCity());
			existingStation.setNumberOfPorts(updatedStation.getNumberOfPorts());
			
			session.merge(existingStation);
			transaction.commit();
			return existingStation;
		}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
	
	public String deleteStation(int stationId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			StationModel existingStation = session.get(StationModel.class, stationId);
			session.remove(existingStation);
			transaction.commit();
			return "Station deleted successfully!!!";
		}catch (Exception e) {
			transaction.rollback();
			throw e;
		}finally {
			session.close();
		}
	}
	
	public List<StationModel> getStationsByCity(String city){
		Session session = sessionFactory.openSession();
		
		try {
			return session.createQuery("from StationModel where city = :city", StationModel.class).setParameter("city",city).list();
		}finally {
			session.close();
		}
	}
}