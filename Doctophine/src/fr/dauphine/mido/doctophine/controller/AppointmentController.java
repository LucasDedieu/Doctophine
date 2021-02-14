package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;
import fr.dauphine.mido.doctophine.service.MailService;

public class AppointmentController extends AbstractController {
	private int id;
	private int week =-1;
	private int year = -1;
	private boolean opCancel;
	
	CalendarService cs;
	EntityService es;
	
	public AppointmentController() throws NamingException {
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
		es = (EntityService)ic.lookup("java:module/EntityService");
	}

	@Override
	public void init() throws IOException {
		super.init();
		if(opCancel) {
			performCancel();
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
	public void setWeek(int week) {
		this.week = week;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setOpCancel(boolean opCancel) {
		this.opCancel = opCancel;
	}

	private void performCancel() throws IOException {
		cs.cancelAppointment(id);
		Appointment app = es.getAppointment(id);
		if(request.getSession().getAttribute("patient")!=null) {
			response.sendRedirect("patient.jsp");
		}
		else if(request.getSession().getAttribute("doctor")!=null) {
			String subject = "Annulation de rendez-vous";
			SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM YYYY à HH:mm");
			String dateStr=sdf.format(app.getStartDate());
			String text = "Bonjour, votre rendez vous avec le docteur "+app.getActivity().getDoctor().getFullName()+" du "+dateStr+" a été annulé. Nous nous excusons de la gène occasionée. Nous vous invitons à revenir sur notre plateforme afin de reprendre un rendez-vous.";
			MailService.getInstance().sendMail(subject, text, app.getPatient().getEmail());
			if(week<0 || year<0) {
				response.sendRedirect("calendar.jsp");
			}
			else{
				response.sendRedirect("calendar.jsp?week="+week+"&year="+year);
			}
		}
		
	}

	
}
