package fr.dauphine.mido.doctophine.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MedicalCenterService extends Repository<MedicalCenter>{
	
	@PersistenceContext
    EntityManager em;

	@Override
	public List<MedicalCenter> getAll() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select m from MedicalCenter m");
        return query.getResultList();
	}

	@Override
	public MedicalCenter findById(long id) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT m from MedicalCenter m where m.id=:id");
        query.setParameter("id", id);
        
        return (MedicalCenter) query.getResultList().stream().findFirst().orElse(null);
	}
	
	public MedicalCenter findByName(String name) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("SELECT m from MedicalCenter m where m.name=:name");
        query.setParameter("name", name);
        
        return (MedicalCenter) query.getResultList().stream().findFirst().orElse(null);
	}
 

	@Override
	public void save(MedicalCenter t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

}
