package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import fr.dauphine.mido.doctophine.service.DoctophineService;

public class AppointmentController extends AbstractController {
	private DoctophineService ds = DoctophineService.getInstance();
	private int id;
	private boolean opCancel;
	
	

	public void init() throws IOException {
		if(opCancel) {
			performCancel();
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setOpCancel(boolean opCancel) {
		this.opCancel = opCancel;
	}

	private void performCancel() throws IOException {
		ds.cancelAppointment(id);
		response.sendRedirect("patient.jsp");
		
	}

	
}
