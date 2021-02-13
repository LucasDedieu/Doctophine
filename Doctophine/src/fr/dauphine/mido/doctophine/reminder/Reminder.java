package fr.dauphine.mido.doctophine.reminder;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;

public class Reminder {
	Timer timer;
	
	CalendarService cs;
	
	
	public Reminder(int day) throws NamingException {
		timer = new Timer();
		timer.schedule(new RemindTask(), day*24*60*60*1000);
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
	}
	
	public class RemindTask extends TimerTask{
		@Override
		public void run() {
			List<Appointment> appointmentList = cs.getTodayAppointment(); 
			for(Appointment app : appointmentList) {
				sendReminder(app);
			}
		}
		
		private void sendReminder(Appointment app) {
			Patient patient = app.getPatient();
			
		}
	}
}
