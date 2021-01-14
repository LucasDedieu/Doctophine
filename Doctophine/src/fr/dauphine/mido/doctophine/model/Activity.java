package fr.dauphine.mido.doctophine.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity @Table(name="activity")
public class Activity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "doctor_id", referencedColumnName = "id")
	private Doctor doctor;
	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "medical_center_id", referencedColumnName = "id")
	private MedicalCenter medicalCenter;
	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "speciality_id", referencedColumnName = "id")
	private Speciality speciality;
	
	public int getId() {
		return id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public MedicalCenter getMedicalCenter() {
		return medicalCenter;
	}

	public void setMedicalCenter(MedicalCenter medicalCenter) {
		this.medicalCenter = medicalCenter;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	
}
