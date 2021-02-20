package fr.dauphine.mido.doctophine.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Speciality;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SpecialityService extends Repository<Speciality>{
	
	@PersistenceContext
    EntityManager em;

	@Override
	public List<Speciality> getAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select s from Speciality s");
        return query.getResultList();
	}

	@Override
	public Speciality findById(long id) {
		return  em.find(Speciality.class, (int)id);
	}
 
	
	public Speciality findByName(String name) {
		Query query = em.createQuery("SELECT s from Speciality s where s.name=:name");
        query.setParameter("name", name);
        
        return (Speciality) query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void save(Speciality t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

}
