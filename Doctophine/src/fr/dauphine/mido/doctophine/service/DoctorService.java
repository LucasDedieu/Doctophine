package fr.dauphine.mido.doctophine.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.Patient;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DoctorService extends Repository<Doctor>{
	
	@PersistenceContext
	EntityManager em ;

	@Override
	public List<Doctor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Doctor findById(long id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT d from Doctor d where d.id=:id");
        query.setParameter("id", id);
        
        return (Doctor) query.getResultList();
	}
	
	public void update(Doctor d) {
		
		em.merge(d);
	}
	 
	
	public Doctor findByEmail(String email) {

		Query query = em.createQuery("SELECT d from Doctor d where d.email=:email");
        query.setParameter("email", email);
        
        return (Doctor) query.getResultList().stream().findFirst().orElse(null);
	}
	
	

	@Override
	public void save(Doctor t) {
		// TODO Auto-generated method stub
		em.persist(t);
	}
	
	

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	} 
 

}
