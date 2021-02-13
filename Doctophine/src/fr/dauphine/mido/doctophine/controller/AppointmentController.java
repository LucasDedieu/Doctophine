package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.DoctophineService;

public class AppointmentController extends AbstractController {
	private int id;
	private String from;
	private boolean opCancel;
	
	CalendarService cs;
	
	public AppointmentController() throws NamingException {
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
	}

	public void init() throws IOException {
		if(opCancel) {
			performCancel(from);
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	

	public void setOpCancel(boolean opCancel) {
		this.opCancel = opCancel;
	}

	private void performCancel(String from) throws IOException {
		cs.cancelAppointment(id);
		if(from.equals("patient")) {
			response.sendRedirect("patient.jsp");
		}
		else if(from.equals("doctor")) {
			response.sendRedirect("calendar.jsp");
		}
		
	}

	
}
