package fr.dauphine.mido.doctophine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="patient")
public class Patient extends AbstractAccount {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
}
