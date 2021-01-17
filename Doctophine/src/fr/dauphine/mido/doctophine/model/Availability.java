package fr.dauphine.mido.doctophine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="availability")
public class Availability extends AbstractEvent {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	public int getId() {
		return id;
	}

	
	public String toString() {
		return("Disponibilit�  du "+activity.getDoctor()+" au centre medical"+activity.getMedicalCenter()+" de "+startDate+" � "+endDate+".");
	}
}
