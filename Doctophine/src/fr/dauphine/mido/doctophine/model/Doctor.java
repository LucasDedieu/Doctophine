package fr.dauphine.mido.doctophine.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

@Entity @Table(name="doctor")
public class Doctor extends AbstractAccount {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	public int getId() {
		return id;
	}
	
	
	public List<MedicalCenter> getMedicalCenterList(){
		EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("Doctophine");
            entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Activity> query = entityManager.createQuery( "FROM Activity WHERE doctor = ?1", Activity.class);
            List<Activity> activityList = query.setParameter(1, this).getResultList();
            List<MedicalCenter> medicalCenterList= new ArrayList<>();
            for(Activity activity : activityList) {
            	medicalCenterList.add(activity.getMedicalCenter());
            }
       
            return medicalCenterList;
        }
        finally {
            if ( entityManager != null ) entityManager.close();
            if ( entityManagerFactory != null ) entityManagerFactory.close();
        }
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Dr. "+super.toString();
	}
}