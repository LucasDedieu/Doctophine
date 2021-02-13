package fr.dauphine.mido.doctophine.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="patient")
public class Patient extends AbstractAccount {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
	public int getId() {
		return id;
	}
	
	public Patient() {
		
	}
	
	
	public Patient(String firstName, String lastName, String address, String email, String password, Date birthDate, String phone) {
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setEmail(email);
		setPassword(password);
		setBirthDate(birthDate);
		setPhone(phone);
		setCreationDate(new Date());	
	}
}
