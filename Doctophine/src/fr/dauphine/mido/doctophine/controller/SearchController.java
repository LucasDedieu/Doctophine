package fr.dauphine.mido.doctophine.controller;

import java.util.Collections;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.DoctophineService;
import fr.dauphine.mido.doctophine.service.EntityService;
import fr.dauphine.mido.doctophine.service.SearchService;

public class SearchController extends AbstractController {
	
	private String name="";
	private boolean opSearch;
	MedicalCenter medicalCenter;
	Speciality speciality;
	
	List<Activity> activities = Collections.emptyList();
	
	SearchService ss;
	EntityService es;
	
	public SearchController() throws NamingException {
		InitialContext ic = new InitialContext();
		ss = (SearchService)ic.lookup("java:module/SearchService");
		es = (EntityService)ic.lookup("java:module/EntityService");
	}
	
	
	public String getName() {
		return name;
	}



	
	public void init() {
		if(opSearch) {
			if(speciality!=null || medicalCenter!=null) {
				activities = ss.findActivities(name, speciality, medicalCenter);
			}
			else {
				activities = ss.findActivities(name, null, null);
			}
		}
	}
	
	
	public List<Speciality> getAllSpecialities(){
		return es.getAllSpecialities();
	}
	
	public List<MedicalCenter> getAllMedicalCenters(){
		return es.getAllMedicalCenters();
	}
	
	
	public void setOpSearch(boolean opSearch) {
		this.opSearch = opSearch;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMedicalCenter(int id) {
		this.medicalCenter = es.getMedicalCenter(id);
	}
	
	public void setSpeciality(int id) {
		this.speciality = es.getSpeciality(id);
	}
	
	public List<Activity> getActivityList(){
		return activities;
	}
	


}
