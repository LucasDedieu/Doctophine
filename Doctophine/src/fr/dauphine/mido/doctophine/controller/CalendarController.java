package fr.dauphine.mido.doctophine.controller;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	public List<Appointment> getAppointmentList(){
		Activity activity = ds.getActivity(getLoggedDoctor(), medicalCenter);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Date startDate = cal.getTime();
		Date endDate = new Date(startDate.getTime() + MILLIS_IN_WEEK);
		
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Appointment> query = entityManager.createQuery("FROM Appointment WHERE activity = ?1 AND startDate >= :startDate AND endDate <= :endDate", Appointment.class);
            List<Appointment>abstractEventList = query.setParameter(1, activity).
            		setParameter("startDate", startDate).
            		setParameter("endDate", endDate).
            		getResultList();
         
            
       
            return abstractEventList;
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	public void setOpNext(boolean opNext) {
		this.opNext = opNext;
	}



	public void setOpPrev(boolean opPrev) {
		this.opPrev = opPrev;
	}

	public List<List<AbstractEvent>> getCalendar(){
		return ds.getCalendar(getAppointmentList(), week, year);
	}
	
}
