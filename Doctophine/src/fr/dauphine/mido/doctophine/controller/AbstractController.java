package fr.dauphine.mido.doctophine.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.DoctophineService;

public class AbstractController {
	protected DoctophineService ds = DoctophineService.getInstance();
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public void setRequest(HttpServletRequest request) {
		if (this.request != request) {
			this.request = request;
		}
	}

	public void setResponse(HttpServletResponse response) {
		if (this.response != response) {
			this.response = response;
		}
	}
	
	public List<Speciality> getAllSpecialities(){
		return ds.getAllSpecialities();
	}
	
	public List<MedicalCenter> getAllMedicalCenters(){
		return ds.getAllMedicalCenters();
	}
}
