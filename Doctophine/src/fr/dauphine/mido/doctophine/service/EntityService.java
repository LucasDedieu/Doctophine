package fr.dauphine.mido.doctophine.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Availability;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EntityService {


	@PersistenceContext
	EntityManager entityManager ;



	public <T> T getData(Class<T> dataClass, int id) {

		T data = entityManager.find(dataClass, id);
		return data;

	}


	public Doctor getDoctor(int id) {
		return getData(Doctor.class, id);
	}

	public Activity getActivity(int id) {
		return getData(Activity.class, id);
	}

	public Patient getPatient(int id) {
		return getData(Patient.class, id);
	}

	public Appointment getAppointment(int id) {
		return getData(Appointment.class, id);
	}

	public Availability getAvailability(int id) {
		return getData(Availability.class, id);
	}

	public MedicalCenter getMedicalCenter(int id) {
		return getData(MedicalCenter.class, id);
	}

	public Speciality getSpeciality(int id) {
		return getData(Speciality.class, id);
	}

	/**
	 * 
	 * @param doctor
	 * @param medicalCenter
	 * @return the activity for a given doctor and medical center
	 */
	public Activity getActivity(Doctor doctor, MedicalCenter medicalCenter) {

		TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = ?1 AND medicalCenter = ?2", Activity.class);
		List<Activity> activityList = query.setParameter(1, doctor).setParameter(2,medicalCenter).getResultList();
		if(activityList == null || activityList.size() == 0) {
			return null;
		}
		return activityList.get(0);
	}




	/**
	 * 
	 * @return all the specialities
	 */
	public List<Speciality> getAllSpecialities() {

		TypedQuery<Speciality> query = entityManager.createQuery("FROM Speciality ORDER BY name", Speciality.class);
		List<Speciality> specialities= query.getResultList();

		return specialities;

	}

	/**
	 * 
	 * @return all the medical centers
	 */
	public List<MedicalCenter> getAllMedicalCenters() {

		TypedQuery<MedicalCenter> query = entityManager.createQuery("FROM MedicalCenter ORDER BY name", MedicalCenter.class);
		List<MedicalCenter> medicalCenters= query.getResultList();

		return medicalCenters;

	}


	/**
	 * 
	 * @param doctor
	 * @return all the medical center of a given doctor
	 */
	public List<MedicalCenter> getMedicalCenterList(Doctor doctor){

		TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = :doctor", Activity.class);
		List<Activity> activityList = query.setParameter("doctor", doctor).getResultList();
		List<MedicalCenter> medicalCenterList= new ArrayList<>();
		for(Activity activity : activityList) {
			medicalCenterList.add(activity.getMedicalCenter());
		}
		return medicalCenterList;
	}



}
