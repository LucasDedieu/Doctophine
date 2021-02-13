package fr.dauphine.mido.doctophine.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Patient;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AppointmentService extends Repository<Appointment>{
	
	@PersistenceContext
	EntityManager em ; 

	@Override
	public List<Appointment> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appointment findById(long id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT a from Appointment a where a.patient_ID=:id");
        query.setParameter("patient_ID", id);
        
        return (Appointment) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findAllByPatientId(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT a from Appointment a where a.patient.id=:id");
        query.setParameter("id", id);
        
        return (List<Appointment>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> findAllByActivityId(int id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT a from Appointment a where a.activity.id=:id");
        query.setParameter("id", id);
        
        return (List<Appointment>) query.getResultList();
	}
	 
 
	
	public void update(Appointment t) {
		em.merge(t);
	}
	 

	@Override
	public void save(Appointment t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

}
