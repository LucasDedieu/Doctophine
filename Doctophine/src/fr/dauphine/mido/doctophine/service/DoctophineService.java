package fr.dauphine.mido.doctophine.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.dauphine.mido.doctophine.model.AbstractEvent;
import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Availability;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;

public class DoctophineService {
	private static final Long MILLIS_IN_WEEK = 6*24*60*60*1000L;
	private static final Long MILLIS_IN_MIN = 60*1000L;
	private static final DoctophineService INSTANCE = new DoctophineService();
	

	
	private DoctophineService() {
		
	}
	
	public static DoctophineService getInstance() {
		return INSTANCE;
	}
	
	
	
	public List<MedicalCenter> getMedicalCenterList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<MedicalCenter> medicalCenterList = entityManager.createQuery( "from MedicalCenter", MedicalCenter.class ).getResultList();
            return medicalCenterList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<Speciality> getSpecialityList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Speciality> specialityList = entityManager.createQuery( "from Speciality", Speciality.class ).getResultList();
            return specialityList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<Activity> getActivityList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Activity> activityList = entityManager.createQuery( "from Activity", Activity.class ).getResultList();
            return activityList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	
	public List<Doctor> getDoctorList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Doctor> doctorList = entityManager.createQuery( "from Doctor", Doctor.class ).getResultList();
            return doctorList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	public void insertMedicalCenter(MedicalCenter medicalCenter) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(medicalCenter);
            entityManager.getTransaction().commit();

        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<Patient> getPatientList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            List<Patient> patientList = entityManager.createQuery( "from Patient", Patient.class ).getResultList();
            return patientList;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public <T> T getData(Class<T> dataClass, int id) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            T data = entityManager.find(dataClass, id);
            return data;
         
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public Doctor getDoctor(int id) {
		return getData(Doctor.class, id);
	}
	
	public Patient getPatient(int id) {
		return getData(Patient.class, id);
	}
	
	public Appointment getAppointment(int id) {
		return getData(Appointment.class, id);
	}
	
	public Availability getAvailability(int id) {
		return getData(Availability.class, id);
	}
	
	public MedicalCenter getMedicalCenter(int id) {
		return getData(MedicalCenter.class, id);
	}
	
	
	public Activity getActivity(Doctor doctor, MedicalCenter medicalCenter) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = ?1 AND medicalCenter = ?2", Activity.class);
            List<Activity> activityList = query.setParameter(1, doctor).setParameter(2,medicalCenter).getResultList();
            if(activityList == null || activityList.size() == 0) {
            	return null;
            }
            return activityList.get(0);
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
	public List<List<AbstractEvent>> getCalendar(Doctor doctor, MedicalCenter medicalCenter, int week, int year){
		List<AbstractEvent> eventList = getEventList(doctor, medicalCenter, week, year);
		
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
	
	public List<AbstractEvent> getEventList(Doctor doctor, MedicalCenter medicalCenter, int week, int year){
		List<AbstractEvent> eventList = new ArrayList<>(); 
		Activity activity = getActivity(doctor, medicalCenter);
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
            TypedQuery<Appointment> query1 = entityManager.createQuery("FROM Appointment WHERE activity = :activity AND startDate >= :startDate AND endDate <= :endDate", Appointment.class);
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
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	public void addAvailabilities(List<Date> startDates, MedicalCenter medicalCenter, Doctor doctor) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {

            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = :doctor AND medicalCenter = :medicalCenter", Activity.class);
            List<Activity> activityList = query.setParameter("doctor", doctor).setParameter("medicalCenter",medicalCenter).getResultList();
            Activity activity = activityList.get(0);
        	for(Date startDate : startDates) {
        		Availability availability = buildAvailability(startDate, activity);
        		entityManager.persist(availability);
        	}
        	
            entityManager.getTransaction().commit();

        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	private Availability buildAvailability(Date startDate, Activity activity) {
		Availability availability = new Availability();
		availability.setStartDate(startDate);
		availability.setEndDate(new Date(startDate.getTime()+30*MILLIS_IN_MIN));
		availability.setActivity(activity);
		return availability;
		
	}

	public void deleteAvailabilities(List<Date> startDates, MedicalCenter medicalCenter, Doctor doctor) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
        	entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = :doctor AND medicalCenter = :medicalCenter", Activity.class);
            List<Activity> activityList = query.setParameter("doctor", doctor).setParameter("medicalCenter",medicalCenter).getResultList();
            Activity activity = activityList.get(0);
            for(Date startDate : startDates) {
	        	entityManager.createQuery("delete from Availability WHERE activity = :activity AND startDate = :startDate")
	        	  .setParameter("activity", activity)
	        	  .setParameter("startDate", startDate)
	        	  .executeUpdate();
            }
        	
            entityManager.getTransaction().commit();

        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}

	public List<Appointment> getNextAppointments(Patient patient) {
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            Date date = new Date();
            TypedQuery<Appointment> query = entityManager.createQuery("FROM Appointment WHERE patient = :patient AND startDate >= :date ORDER BY startDate", Appointment.class);
            List<Appointment> appointmentList = query.setParameter("patient",patient).setParameter("date",date).getResultList();




            if(appointmentList == null || appointmentList.size() == 0) {
            	return null;
            }
            return appointmentList;
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}
	
	
}
