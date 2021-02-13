package fr.dauphine.mido.doctophine.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import fr.dauphine.mido.doctophine.model.Patient; 
 
 
@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PatientService extends Repository<Patient>{
	 
	 
	@PersistenceContext
	EntityManager em ; 
	 

	@Override
	public List<Patient> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Patient t) {
		// TODO Auto-generated method stub
		em.persist(t);
	} 
	
	public void update(Patient t) {
		em.merge(t);
	}

	@Override
	public void deleteById(long id) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked") 
	public Patient findByEmail(String email) {
		 
		Query query = em.createQuery("SELECT p from Patient p where p.email=:email");
        query.setParameter("email", email);
        
        return (Patient) query.getResultList().stream().findFirst().orElse(null);
	}


}
 