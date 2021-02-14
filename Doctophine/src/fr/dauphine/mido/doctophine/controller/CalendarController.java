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
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.service.CalendarService;
import fr.dauphine.mido.doctophine.service.EntityService;

public class CalendarController extends AbstractController{
	private static final Long MILLIS_IN_WEEK = 6*24*60*60*1000L;
	private MedicalCenter medicalCenter;
	private int week;
	private int year;
	private boolean opNext;
	private boolean opPrev;
	private boolean opEnable;
	private boolean opDisable;
	private String[] slots;
	
	
	CalendarService cs;
	EntityService es;
	
	public CalendarController() throws NamingException {
		InitialContext ic = new InitialContext();
		cs = (CalendarService)ic.lookup("java:module/CalendarService");
		es = (EntityService)ic.lookup("java:module/EntityService");
	}
	
	
	
	
	
	@Override
	public void init() throws IOException {
		super.init();
		if(!(loggedAccount instanceof Doctor)) {
			response.sendRedirect("index.jsp");
			return;
		}
		if(week == 0) {
			week = getCurrentWeek();
		}
		if(year == 0) {
			year = getCurrentYear();
		}
		if(medicalCenter == null) {
			medicalCenter = getDefaultMedicalCenter();
		}
		if(opNext) {
			performNext();
			
		}
		if(opPrev) {
			performPrev();
		}
		if(opEnable) {
			processEnable();
		}
		if(opDisable) {
			processDisable();
		}		
	}
	
	


	private void performNext(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		week = calendar.get(Calendar.WEEK_OF_YEAR);
		year = calendar.get(Calendar.YEAR);
	}
	
	private void performPrev(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		week= calendar.get(Calendar.WEEK_OF_YEAR);
		year = calendar.get(Calendar.YEAR);
	}


	public void setMedicalCenter(int id) {
		this.medicalCenter = es.getMedicalCenter(id);
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
	
	public Doctor getLoggedDoctor() {
		return (Doctor)request.getSession().getAttribute("doctor");
	}
	
	public int getWeek() {
		return week;
	}
	
	public int getYear() {
		return year;
	}
	
	public MedicalCenter getCurrentMedicalCenter() {
		return medicalCenter;
	}
	
	
	private MedicalCenter getDefaultMedicalCenter() {
		return es.getMedicalCenterList(getLoggedDoctor()).get(0); //FIXME
	}
	
	public List<MedicalCenter> getMedicalCenterList(){
		return es.getMedicalCenterList(getLoggedDoctor());
	}
	
	
	public void setOpNext(boolean opNext) {
		this.opNext = opNext;
	}



	public void setOpPrev(boolean opPrev) {
		this.opPrev = opPrev;
	}
	
	
	public void setOpDisable(boolean opDisable) {
		this.opDisable = opDisable;
	}


	public void setOpEnable(boolean opEnable) {
		this.opEnable = opEnable;
	}

	public List<List<AbstractEvent>> getCalendar(){
		return cs.getCalendar(getLoggedDoctor(), medicalCenter, week, year);
	}
	
	
	public void setSlots(String[] slots) {	
		this.slots = slots;
	}
	
	
	public List<Date> getSlotsDateList(){
		if(slots ==null) {
			return Collections.emptyList();
		}
		List<Date> dates = new ArrayList<>();
		for(String slotStr : slots) {
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
			
			dates.add(calendar.getTime());
		}
		return dates;
	}
	
	private void processDisable() {
		List<Date> dates = getSlotsDateList();
		cs.deleteAvailabilities(dates, medicalCenter, getLoggedDoctor());
		
	}

	private void processEnable() {
		List<Date> dates = getSlotsDateList();
		boolean conflict = cs.addAvailabilities(dates, medicalCenter, getLoggedDoctor());
		if(conflict) {
			request.setAttribute("calendar.conflict",true);
		}
		
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
	
}
