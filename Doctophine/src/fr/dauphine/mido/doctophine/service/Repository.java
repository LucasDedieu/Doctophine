package fr.dauphine.mido.doctophine.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class Repository<T>  { 

    public abstract List<T> getAll();

    public abstract T findById(long id); 

    public abstract void save(T t);

    public abstract void deleteById(long id);
}
