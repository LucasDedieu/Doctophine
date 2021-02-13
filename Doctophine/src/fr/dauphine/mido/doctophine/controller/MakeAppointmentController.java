package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.dauphine.mido.doctophine.model.AbstractEvent;
import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;

public class MakeAppointmentController extends AbstractController {
	private static final Long MILLIS_IN_WEEK = 6*24*60*60*1000L;
	private Activity activity;
	private int week;
	private int year;
	private boolean opNext;
	private boolean opPrev;
	private boolean opSelect;
	private String description;
	private String slotStr;
	
	
	CalendarService cs;
	EntityService es;
	
	
	public MakeAppointmentController() throws NamingException {
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
		es = (EntityService)ic.lookup("java:module/EntityService");
	}
	
	
	public void init() throws IOException {
		if(week == 0) {
			week = getCurrentWeek();
		}
		if(year == 0) {
			year = getCurrentYear();
		}
		if(opNext) {
			week = getNextWeek();
		}
		if(opPrev) {
			week=getPrevWeek();
		}
		if(opSelect) {
			processSelect();
		}

	}


	private int getNextWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	private int getPrevWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public void setActivity(int id) {
		this.activity = es.getActivity(id);
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Activity getCurrentActivity() {
		return activity;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	private int getCurrentWeek() { 
		return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
	}
	
	private int getCurrentYear() { 
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public Patient getLoggedPatient() {
		return es.getPatient(1);//FIXME
	}
	
	public int getWeek() {
		return week;
	}
	
	public int getYear() {
		return year;
	}
	
	
	
	public void setOpNext(boolean opNext) {
		this.opNext = opNext;
	}



	public void setOpPrev(boolean opPrev) {
		this.opPrev = opPrev;
	}
	
	
	public void setOpSelect(boolean opSelect) {
		this.opSelect= opSelect;
	}


	public List<List<AbstractEvent>> getCalendar(){
		return cs.getCalendar(activity.getDoctor(), activity.getMedicalCenter(), week, year);
	}
	
	
	public void setSlot(String slot) {	
		this.slotStr = slot;
	}
	
	
	public Date getSlotDate(){
		if(slotStr ==null || slotStr.length()==0) {
			return null;
		}

		String[] coord = slotStr.split(" ");
		int day = Integer.parseInt(coord[1]);
		int slot = Integer.parseInt(coord[0]);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.DAY_OF_WEEK, day);
		calendar.set(Calendar.HOUR_OF_DAY, 8+ (int)slot/2);
		calendar.set(Calendar.MINUTE, slot%2==0?0:30);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
		

	}
	

	public int[] getDaysOfWeek() {
		return cs.getDaysOfWeek();
	}
	
	public List<String> getDayNames(){
		List<String> dayNames = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		int[] daysOfWeek =  cs.getDaysOfWeek();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM YYYY");
		for(int i = 0; i < 7; i++) {
			int day = daysOfWeek[i];
			
			calendar.set(Calendar.DAY_OF_WEEK, day);
			Date date = calendar.getTime();
			String name = sdf.format(date);
			dayNames.add(name);
		}
		return dayNames;
	}
	

	private void processSelect() throws IOException {
		Date date = getSlotDate();
		if(date==null) {
			return;
		}
		cs.addAppointment(date, activity.getId(), getLoggedPatient().getId(), description);
		response.sendRedirect("patient.jsp");
		
		
	}

}
