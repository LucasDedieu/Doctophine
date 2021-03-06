package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.DoctorService;
import fr.dauphine.mido.doctophine.service.PatientService;

/**
 * Servlet implementation class ModificationMotDePasseController
 */
@WebServlet("/ModificationMotDePasseController")
public class ModificationMotDePasseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	PatientService patientService;
	@EJB
	DoctorService doctorService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationMotDePasseController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		Patient patient = (Patient) session.getAttribute("patient");
		Doctor doctor = (Doctor) session.getAttribute("doctor"); 
		RequestDispatcher requestDispatcher = null; 
		System.out.println("1");
		if (patient != null) {
			 
			System.out.println(patient.getFirstName());
			requestDispatcher = request.getRequestDispatcher("password_maj.jsp");
			request.setAttribute("patient", patient);
			requestDispatcher.include(request, response);
		} else if (doctor != null) {
			 
			System.out.println(doctor.getFirstName());
			requestDispatcher = request.getRequestDispatcher("password_maj.jsp");
			request.setAttribute("doctor", doctor);
			requestDispatcher.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		Patient patient = (Patient) session.getAttribute("patient");
		Doctor doctor = (Doctor) session.getAttribute("doctor"); 
		RequestDispatcher requestDispatcher = null;
		 
		
		if (patient != null) {
	 
			String ex_password = request.getParameter("ex_password"); 
			String new_password = request.getParameter("new_password1");
			
			if (patient.getPassword().equals(ex_password)) {
				 
				patient.setPassword(new_password);
				patientService.update(patient);
				
				requestDispatcher = request.getRequestDispatcher("accueil.jsp");
				request.setAttribute("patient", patient);
				session.setAttribute("patient", patient);
				request.setAttribute("valide_password", "Votre mot de passe a ete modifie");
				requestDispatcher.include(request, response);
			} else {
				 
				requestDispatcher = request.getRequestDispatcher("password_maj.jsp");
				request.setAttribute("patient", patient);
				request.setAttribute("error_password", "Veuillez inserer correctement votre mot de passe actuel");
				session.setAttribute("patient", patient);
				requestDispatcher.include(request, response);
				
			}
			
			
			
			
		} else if (doctor != null) {
			 
			String ex_password = request.getParameter("ex_password"); 
			String new_password = request.getParameter("new_password1");
			
			if (doctor.getPassword().equals(ex_password)) {
				 
				doctor.setPassword(new_password);
				doctorService.update(doctor);
				
				requestDispatcher = request.getRequestDispatcher("accueil.jsp");
				request.setAttribute("doctor", doctor);
				session.setAttribute("doctor", doctor);
				request.setAttribute("valide_password", "Votre mot de passe a ete modifie");
				requestDispatcher.include(request, response);
			} else {
				 
				requestDispatcher = request.getRequestDispatcher("password_maj.jsp");
				request.setAttribute("doctor", doctor);
				request.setAttribute("error_password", "Veuillez inserer correctement votre mot de passe actuel");
				session.setAttribute("doctor", doctor);
				requestDispatcher.include(request, response);
				
			}
		}
		 
		
	}

}
