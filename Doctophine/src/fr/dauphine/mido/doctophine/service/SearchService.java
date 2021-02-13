package fr.dauphine.mido.doctophine.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Speciality;


@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SearchService {


	@PersistenceContext
	EntityManager entityManager ;


	public List<Activity> findActivitiesByDoctorName(String name) {

		TypedQuery<Activity> query = entityManager.createQuery("FROM Activity WHERE lower(doctor.lastName) like lower(concat('%', :name, '%'))", Activity.class);
		List<Activity> activityList = query.setParameter("name",name).getResultList();

		return activityList;

	}



	public List<Activity> findActivities(String name, Speciality speciality, MedicalCenter medicalCenter) {
		if((name==null || name.length()==0) && speciality==null && medicalCenter==null) {
			return Collections.emptyList();
		}

		StringBuilder sb = new StringBuilder();
		if(speciality!=null) {
			sb.append("speciality = :speciality");
		}
		if(medicalCenter!=null) {
			if(sb.length()>0) {
				sb.append(" AND ");
			}
			sb.append("medicalCenter = :medicalCenter");
		}
		if(name!=null) {
			if(sb.length()>0) {
				sb.append(" AND ");
			}
			sb.append("lower(doctor.lastName) like lower(concat('%', :name, '%'))");
		}

		TypedQuery<Activity> query = entityManager.createQuery("FROM Activity WHERE "+sb.toString(), Activity.class);
		if(speciality!=null) {
			query.setParameter("speciality",speciality);
		}
		if(medicalCenter!=null) {
			query.setParameter("medicalCenter",medicalCenter);
		}
		if(name!=null) {
			query.setParameter("name",name);
		}
		List<Activity> activityList = query.getResultList();

		return activityList;

	}





	public List<Speciality> getAllSpecialities() {

		TypedQuery<Speciality> query = entityManager.createQuery("FROM Speciality ORDER BY name", Speciality.class);
		List<Speciality> specialities= query.getResultList();

		return specialities;

	}

	public List<MedicalCenter> getAllMedicalCenters() {

		TypedQuery<MedicalCenter> query = entityManager.createQuery("FROM MedicalCenter ORDER BY name", MedicalCenter.class);
		List<MedicalCenter> medicalCenters= query.getResultList();

		return medicalCenters;

	}



}
