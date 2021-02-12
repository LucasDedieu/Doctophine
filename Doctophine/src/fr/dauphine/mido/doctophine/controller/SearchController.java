package fr.dauphine.mido.doctophine.controller;

import java.util.Collections;
import java.util.List;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.DoctophineService;

public class SearchController extends AbstractController {
	
	private String name="";
	private boolean opSearch;
	MedicalCenter medicalCenter;
	Speciality speciality;
	
	List<Activity> activities = Collections.emptyList();
	
	
	public String getName() {
		return name;
	}



	
	public void init() {
		if(opSearch) {
			if(speciality!=null || medicalCenter!=null) {
				activities = ds.findActivities(name, speciality, medicalCenter);
			}
			else {
				activities = ds.findActivities(name, null, null);
			}
		}
	}
	
	
	public void setOpSearch(boolean opSearch) {
		this.opSearch = opSearch;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMedicalCenter(int id) {
		this.medicalCenter = ds.getMedicalCenter(id);
	}
	
	public void setSpeciality(int id) {
		this.speciality = ds.getSpeciality(id);
	}
	
	public List<Activity> getActivityList(){
		return activities;
	}
	


}
