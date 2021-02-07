package fr.dauphine.mido.doctophine.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity @Table(name="appointment")
public class Appointment extends AbstractEvent {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "patient_ID", referencedColumnName = "id")
	private Patient patient;
	
	
	@Column(name="description")
	private String description;
	
	@Column(name="is_video")
	private boolean isVideo;
	
	@Column(name="is_cancelled")
	private boolean isCancelled;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVideo() {
		return isVideo;
	}

	public void setVideo(boolean isVideo) {
		this.isVideo = isVideo;
	}
	
	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public int getId() {
		return id;
	}
	
	
	public String toString() {
		return("Rendez_vous pour "+patient+" avec "+activity.getDoctor()+" au centre medical"+activity.getMedicalCenter()+" de "+startDate+" à "+endDate+".");
	}

}
