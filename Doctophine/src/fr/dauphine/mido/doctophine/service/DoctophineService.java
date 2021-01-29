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
	
	
	public List<List<AbstractEvent>> getCalendar(List<Appointment> appointments, int week, int year){
		List<List<AbstractEvent>> agenda = new ArrayList<>();
		
		int[] days = new int[] {
				Calendar.MONDAY, 
				Calendar.TUESDAY, 
				Calendar.WEDNESDAY, 
				Calendar.THURSDAY, 
				Calendar.FRIDAY, 
				Calendar.SATURDAY, 
				Calendar.SUNDAY };

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
				Appointment slotAppointment = null;
				long slotTime = slotDate.getTime()/1000;
				for(Iterator<Appointment> it = appointments.iterator();it.hasNext();) {
					Appointment appointment = it.next();
					long appointmentTime = appointment.getStartDate().getTime()/1000;
					if(appointmentTime==slotTime) {
						slotAppointment= appointment;
						it.remove();
						break;
					}
				}
				if(slotAppointment != null) {
					dayList.add(slotAppointment);
				}
				else {
					dayList.add(null);
				}
			}
		}
		
		return agenda;
	}
	
	
}
