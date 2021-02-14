package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;

public class PatientController extends AbstractController {
	private int week;
	private int year;
	private boolean opNext;
	private boolean opPrev;
	
	CalendarService cs;
	EntityService es;
	
	public PatientController() throws NamingException {
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
		es = (EntityService)ic.lookup("java:module/EntityService");
	}

	@Override
	public void init() throws IOException {
		super.init();
		if(!(loggedAccount instanceof Patient)) {
			response.sendRedirect("index.jsp");
			return;
		}
	}
	
	public List<Speciality> getAllSpecialities(){
		return es.getAllSpecialities();
	}
	
	public List<MedicalCenter> getAllMedicalCenters(){
		return es.getAllMedicalCenters();
	}
	
	public Patient getLoggedPatient() {
		return (Patient)request.getSession().getAttribute("patient");
	}
	

	public List<Appointment> getNextAppointments(){
		return cs.getNextAppointments(getLoggedPatient());
	}
	
	public List<Appointment> getPreviousAppointments(){
		return cs.getPreviousAppointments(getLoggedPatient());
	}
	
	public String getName() {
		return"";
	}
}
