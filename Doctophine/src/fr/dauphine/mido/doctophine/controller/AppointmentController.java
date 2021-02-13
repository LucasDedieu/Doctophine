package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import fr.dauphine.mido.doctophine.service.DoctophineService;

public class AppointmentController extends AbstractController {
	private int id;
	private String from;
	private boolean opCancel;
	
	

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
		ds.cancelAppointment(id);
		if(from.equals("patient")) {
			response.sendRedirect("patient.jsp");
		}
		else if(from.equals("doctor")) {
			response.sendRedirect("calendar.jsp");
		}
		
	}

	
}
