package fr.dauphine.mido.doctophine.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.doctophine.model.AbstractEvent;
import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.service.DoctophineService;

public class CalendarController extends AbstractController{
	private static final Long MILLIS_IN_WEEK = 6*24*60*60*1000L;
	private DoctophineService ds = DoctophineService.getInstance();
	private MedicalCenter medicalCenter;
	private int week;
	private int year;
	private boolean opNext;
	private boolean opPrev;
	private boolean opEnable;
	private boolean opDisable;
	private String[] slots;
	
	
	public void init() {
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
			week = getNextWeek();
		}
		if(opPrev) {
			week=getPrevWeek();
		}
		if(opEnable) {
			processEnable();
		}
		if(opDisable) {
			processDisable();
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

	public void setMedicalCenter(int id) {
		this.medicalCenter = ds.getMedicalCenter(id);
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
		return ds.getDoctor(1);//FIXME
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
		return getLoggedDoctor().getMedicalCenterList().get(0); //FIXME
	}
	
	public List<MedicalCenter> getMedicalCenterList(){
		return getLoggedDoctor().getMedicalCenterList();
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
		return ds.getCalendar(getLoggedDoctor(), medicalCenter, week, year);
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
		ds.deleteAvailabilities(dates, medicalCenter, getLoggedDoctor());
		
	}

	private void processEnable() {
		List<Date> dates = getSlotsDateList();
		ds.addAvailabilities(dates, medicalCenter, getLoggedDoctor());
	}
	
	public int[] getDaysOfWeek() {
		return ds.getDaysOfWeek();
	}
	
	public List<String> getDayNames(){
		List<String> dayNames = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		int[] daysOfWeek =  ds.getDaysOfWeek();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM");
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
