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


	/**
	 * 
	 * @param doctorName 
	 * @return the activities involving a given doctor
	 */
	public List<Activity> findActivitiesByDoctorName(String doctorName) {

		TypedQuery<Activity> query = entityManager.createQuery("FROM Activity WHERE lower(doctor.lastName) like lower(concat('%', :name, '%'))", Activity.class);
		List<Activity> activityList = query.setParameter("name",doctorName).getResultList();

		return activityList;

	}



	/**
	 * Multicriteria search
	 * @param doctorName  
	 * @param speciality
	 * @param medicalCenter
	 * @return the activities matching the given criteria
	 */
	public List<Activity> findActivities(String doctorName, Speciality speciality, MedicalCenter medicalCenter) {
		if((doctorName==null || doctorName.length()==0) && speciality==null && medicalCenter==null) {
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
		if(doctorName!=null) {
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
		if(doctorName!=null) {
			query.setParameter("name",doctorName);
		}
		List<Activity> activityList = query.getResultList();

		return activityList;

	}

}
