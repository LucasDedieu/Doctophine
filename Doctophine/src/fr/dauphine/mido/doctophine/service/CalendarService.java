package fr.dauphine.mido.doctophine.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.doctophine.model.AbstractEvent;
import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Availability;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;

@Stateless 
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CalendarService {
	private static final Long MILLIS_IN_WEEK = 6*24*60*60*1000L;
	private static final Long MILLIS_IN_MIN = 60*1000L;


	@PersistenceContext
	EntityManager entityManager ;




	public Activity getActivity(Doctor doctor, MedicalCenter medicalCenter) {

		TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = ?1 AND medicalCenter = ?2", Activity.class);
		List<Activity> activityList = query.setParameter(1, doctor).setParameter(2,medicalCenter).getResultList();
		if(activityList == null || activityList.size() == 0) {
			return null;
		}
		return activityList.get(0);


	}

	/**
	 * 
	 * @param doctor
	 * @param medicalCenter
	 * @param week
	 * @param year
	 * @return the list of doctor's event of each day of week in the given medicalCenter
	 */
	public List<List<AbstractEvent>> getCalendar(Doctor doctor, MedicalCenter medicalCenter, int week, int year){ 
		List<AbstractEvent> eventList = getEventList(doctor, medicalCenter, week, year);
		//events per day of week
		List<List<AbstractEvent>> agenda = new ArrayList<>();

		int[] days = getDaysOfWeek();

		for(int i = 0; i < days.length; i++) { //car date commencent le dimanche
			int day = days[i];
			List<AbstractEvent> dayList = new ArrayList<>();
			agenda.add(dayList); 
			for(int slot=0; slot<24; slot++) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.WEEK_OF_YEAR, week);
				calendar.set(Calendar.DAY_OF_WEEK, day);
				calendar.set(Calendar.HOUR_OF_DAY, 8+ (int)slot/2);
				calendar.set(Calendar.MINUTE, slot%2==0?0:30);
				calendar.set(Calendar.SECOND, 0);

				Date slotDate = calendar.getTime();
				AbstractEvent slotEvent = null;
				long slotTime = slotDate.getTime()/1000;
				for(Iterator<AbstractEvent> it = eventList.iterator();it.hasNext();) {
					AbstractEvent event = it.next();
					long eventTime = event.getStartDate().getTime()/1000;
					if(eventTime==slotTime) {
						slotEvent= event;
						it.remove();
						break;
					}
				}
				if(slotEvent != null) {
					dayList.add(slotEvent);
				}
				else {
					dayList.add(null);
				}
			}
		}

		return agenda;
	}

	
	public int[] getDaysOfWeek() {
		return new int[] {
				Calendar.MONDAY,
				Calendar.TUESDAY, 
				Calendar.WEDNESDAY, 
				Calendar.THURSDAY, 
				Calendar.FRIDAY, 
				Calendar.SATURDAY, 
				Calendar.SUNDAY };
	}
	
	

	/**
	 * 
	 * @param doctor
	 * @param medicalCenter
	 * @param week
	 * @param year
	 * @return the all the events for a given doctor, medical center, a week and a year
	 */
	public List<AbstractEvent> getEventList(Doctor doctor, MedicalCenter medicalCenter, int week, int year){
		List<AbstractEvent> eventList = new ArrayList<>(); 
		Activity activity = getActivity(doctor, medicalCenter);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);

		Date startDate = cal.getTime();
		Date endDate = new Date(startDate.getTime() + MILLIS_IN_WEEK+(23*60*60*1000));//sunday 23h


		TypedQuery<Appointment> query1 = entityManager.createQuery("FROM Appointment WHERE activity = :activity AND startDate >= :startDate AND endDate <= :endDate AND isCancelled = FALSE", Appointment.class);
		List<Appointment>list1 = query1
				.setParameter("activity", activity)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();
		eventList.addAll(list1);

		TypedQuery<Availability> query2 = entityManager.createQuery("FROM Availability WHERE activity = :activity AND startDate >= :startDate AND endDate <= :endDate", Availability.class);
		List<Availability>list2 = query2
				.setParameter("activity", activity)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();  
		eventList.addAll(list2);
		return eventList;


	}

	
	/**
	 * Add an avaibility to the database for a given doctor, medical center and date
	 * @param startDates
	 * @param medicalCenter
	 * @param doctor
	 */
	public boolean addAvailabilities(List<Date> startDates, MedicalCenter medicalCenter, Doctor doctor) {	
		boolean conflict = false;
		TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = :doctor AND medicalCenter = :medicalCenter", Activity.class);
		List<Activity> activityList = query.setParameter("doctor", doctor).setParameter("medicalCenter",medicalCenter).getResultList();
		Activity activity = activityList.get(0);
		for(Date startDate : startDates) {
			if(doctorIsFree(doctor, startDate)) {
				Availability availability = buildAvailability(startDate, activity);
				entityManager.persist(availability);
			}else {
				conflict=true;
			}
		}
		return conflict;
	}

	

	private boolean doctorIsFree(Doctor doctor, Date startDate) {
		TypedQuery<Availability> query = entityManager.createQuery( "FROM Availability WHERE activity.doctor = :doctor AND startDate = :startDate", Availability.class);
		List<Availability> availabilities = query.setParameter("doctor", doctor).setParameter("startDate",startDate).getResultList();
		return availabilities.isEmpty();
	}

	private Availability buildAvailability(Date startDate, Activity activity) {
		Availability availability = new Availability();
		availability.setStartDate(startDate);
		availability.setEndDate(new Date(startDate.getTime()+30*MILLIS_IN_MIN));
		availability.setActivity(activity);
		return availability;

	}

	
	/**
	 * Delete an avaibility to the database for a given doctor, medical center and date
	 * @param startDate
	 * @param activity
	 * @return
	 */
	public void deleteAvailabilities(List<Date> startDates, MedicalCenter medicalCenter, Doctor doctor) {
		TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = :doctor AND medicalCenter = :medicalCenter", Activity.class);
		List<Activity> activityList = query.setParameter("doctor", doctor).setParameter("medicalCenter",medicalCenter).getResultList();
		Activity activity = activityList.get(0);
		for(Date startDate : startDates) {
			entityManager.createQuery("delete from Availability WHERE activity = :activity AND startDate = :startDate")
			.setParameter("activity", activity)
			.setParameter("startDate", startDate)
			.executeUpdate();
		}

	}

	/**
	 * 
	 * @param patient
	 * @return the future appointments of a given patient
	 */
	public List<Appointment> getNextAppointments(Patient patient) {
		Date date = new Date();
		TypedQuery<Appointment> query = entityManager.createQuery("FROM Appointment WHERE patient = :patient AND startDate >= :date AND isCancelled = FALSE ORDER BY startDate", Appointment.class);
		List<Appointment> appointmentList = query.setParameter("patient",patient).setParameter("date",date).getResultList();

		if(appointmentList == null || appointmentList.size() == 0) {
			return null;
		}
		return appointmentList;
	}

	/**
	 * 
	 * @param patient
	 * @return the past appointments of a given patient
	 */
	public List<Appointment> getPreviousAppointments(Patient patient) {
		Date date = new Date();
		TypedQuery<Appointment> query = entityManager.createQuery("FROM Appointment WHERE patient = :patient AND startDate < :date AND isCancelled = FALSE ORDER BY startDate", Appointment.class);
		List<Appointment> appointmentList = query.setParameter("patient",patient).setParameter("date",date).getResultList();

		if(appointmentList == null || appointmentList.size() == 0) {
			return null;
		}
		return appointmentList;

	}

	
	/**
	 * Cancel an appointment
	 * @param id
	 */
	public void cancelAppointment(int id) {
		Appointment app = entityManager.find(Appointment.class, id);
		app.setCancelled(true);
	}


	/**
	 * Add an appointment to the database for a given patient, activity and date
	 * @param startDate
	 * @param activityId
	 * @param patientId
	 * @param description
	 */
	public void addAppointment(Date startDate, int activityId, int patientId, String description) {		
		Activity activity = entityManager.find(Activity.class, activityId);
		Patient patient = entityManager.find(Patient.class, patientId);
		Appointment appointment = buildAppointment(startDate, activity, patient, description);
		entityManager.persist(appointment);

	}

	private Appointment buildAppointment(Date startDate, Activity activity, Patient patient, String description) {
		Appointment apppointment = new Appointment();
		apppointment.setPatient(patient);
		apppointment.setStartDate(startDate);
		apppointment.setEndDate(new Date(startDate.getTime()+30*MILLIS_IN_MIN));
		apppointment.setActivity(activity);
		apppointment.setDescription(description);
		return apppointment;

	}


	/**
	 * 
	 * @return all the appointments of the day
	 */
	public List<Appointment> getTodayAppointment() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date startDate = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		Date endDate = cal.getTime();


		TypedQuery<Appointment> query = entityManager.createQuery("FROM Appointment WHERE startDate > :startDate AND endDate < :endDate AND isCancelled = FALSE ORDER BY startDate", Appointment.class);
		List<Appointment> appointmentList = query.setParameter("startDate",startDate).setParameter("endDate",endDate).getResultList();

		return appointmentList;
	}


}
