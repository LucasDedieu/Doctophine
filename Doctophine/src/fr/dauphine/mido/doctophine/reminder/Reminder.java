package fr.dauphine.mido.doctophine.reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;
import fr.dauphine.mido.doctophine.service.MailService;

public class Reminder {
	CalendarService cs;

	public Reminder(int startHour, int starMin) {
		try {

			InitialContext ic = new InitialContext();
			cs = (CalendarService)ic.lookup("java:module/CalendarService");
			Timer timer = new Timer();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY,startHour);
			cal.set(Calendar.MINUTE, starMin);
			Date startDate = cal.getTime();
			if(System.currentTimeMillis()>startDate.getTime()) {
				cal.add(Calendar.DAY_OF_WEEK, 1);
				startDate = cal.getTime();
			}
			System.out.println("prochain rappel à "+startDate);
			timer.schedule(new RemindTask(),startDate ,24*60*60*1000);
		} catch (NamingException e) {
			e.printStackTrace();
		}	

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
			SimpleDateFormat sdfFull = new SimpleDateFormat("EEE dd MMM YYYY à HH:mm");
			String dateStrFull=sdfFull.format(app.getStartDate());
			SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
			String dateStrHour=sdfHour.format(app.getStartDate());
			String subject="[Doctophine] Rappel de votre rendez-vous de "+dateStrHour;
			String text="Bonjour "+patient.getFullName()+" , nous vous rappellons que vous avez rendez-vous avec le docteur "+app.getActivity().getDoctor().getFullName()
					+" le "+dateStrFull+" au centre medical "+app.getActivity().getMedicalCenter().getName()+".";
			MailService.getInstance().sendMail(subject, text, patient.getEmail());

		}
	}
}
