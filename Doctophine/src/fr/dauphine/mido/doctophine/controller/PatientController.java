package fr.dauphine.mido.doctophine.controller;

import java.util.Calendar;
import java.util.List;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.DoctophineService;

public class PatientController extends AbstractController {
	private DoctophineService ds = DoctophineService.getInstance();
	private int week;
	private int year;
	private boolean opNext;
	private boolean opPrev;
	
	

	public void init() {

	}
	
	
	public Patient getLoggedPatient() {
		return ds.getPatient(1);//FIXME
	}
	

	public List<Appointment> getNextAppointments(){
		return ds.getNextAppointments(getLoggedPatient());
	}
	
	public List<Appointment> getPreviousAppointments(){
		return ds.getPreviousAppointments(getLoggedPatient());
	}
	
	public String getName() {
		return"";
	}
}
