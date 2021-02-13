package fr.dauphine.mido.doctophine.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Doctor;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ActivityService extends Repository<Activity>{

	@PersistenceContext
	EntityManager em ;
	
	@Override
	public List<Activity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer> findDoctorById(int id){
		
		Query query = em.createQuery("SELECT a.doctor.id from Activity a where a.id=:id");
        query.setParameter("id", id);
        
        return (List<Integer>) query.getResultList();
	}
	
	public List<Activity> findAllByDoctorId(int id){
		
		Query query = em.createQuery("SELECT a from Activity a where a.doctor.id=:id");
        query.setParameter("id", id);
        
        return (List<Activity>) query.getResultList();
	}
	
	 

	@Override
	public void save(Activity t) {
		// TODO Auto-generated method stub
		em.persist(t);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

}
