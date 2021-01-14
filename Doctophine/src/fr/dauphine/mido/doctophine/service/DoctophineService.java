package fr.dauphine.mido.doctophine.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;

public class DoctophineService {
	private static final DoctophineService INSTANCE = new DoctophineService();

	
	private DoctophineService() {
		
	}
	
	public static DoctophineService getInstance() {
		return INSTANCE;
	}
	
	
	
	public List<MedicalCenter> getMedicalCenterList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<MedicalCenter> medicalCenterList = entityManager.createQuery( "from MedicalCenter", MedicalCenter.class ).getResultList();
            return medicalCenterList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	
	public List<Speciality> getSpecialityList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Speciality> specialityList = entityManager.createQuery( "from Speciality", Speciality.class ).getResultList();
            return specialityList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<Activity> getActivityList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Activity> activityList = entityManager.createQuery( "from Activity", Activity.class ).getResultList();
            return activityList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	
	public List<Doctor> getDoctorList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Doctor> doctorList = entityManager.createQuery( "from Doctor", Doctor.class ).getResultList();
            return doctorList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	public void insertMedicalCenter(MedicalCenter medicalCenter) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(medicalCenter);
            entityManager.getTransaction().commit();

        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<Patient> getPatientList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Patient> patientList = entityManager.createQuery( "from Patient", Patient.class ).getResultList();
            return patientList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
}
