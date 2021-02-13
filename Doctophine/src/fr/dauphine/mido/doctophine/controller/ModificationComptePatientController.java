package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Servlet implementation class ModificationComptePatientController
 */
@WebServlet("/ModificationComptePatientController")
public class ModificationComptePatientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	PatientService patientService;
	@EJB
	DoctorService doctorService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationComptePatientController() {
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
		System.out.println("Holé "+patient);
		RequestDispatcher requestDispatcher = null;
		
		if (patient != null) {
			if (request.getParameter("nom")!=null) {
				String nom = request.getParameter("nom");
				patient.setLastName(nom); 
				
			} else if (request.getParameter("prenom")!=null) {
				String prenom = request.getParameter("prenom");
				patient.setFirstName(prenom); 
				
			} else if (request.getParameter("email")!=null) {
				String email = request.getParameter("email");
				patient.setEmail(email); 
				
			} else if (request.getParameter("tel")!=null) {
				String tel = request.getParameter("tel");
				patient.setPhone(tel); 
				
			} else if (request.getParameter("adresse")!=null) {
				String adresse = request.getParameter("adresse");
				patient.setAddress(adresse); 
				
			} else if (request.getParameter("date")!=null) {
				String date = request.getParameter("date");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date_naissance = null; 
				
				try {
					date_naissance = sdf.parse(date); 
				} catch (ParseException e) {
			 
					System.out.println(e.getMessage());
				}
				
				patient.setBirthDate(date_naissance);
				
			}
			
			patientService.update(patient);
			requestDispatcher = request.getRequestDispatcher("infos_patient.jsp");
			request.setAttribute("patient", patient);
			requestDispatcher.include(request, response); 
			
		} else if (doctor != null) {
			
			if (request.getParameter("nom")!=null) {
				String nom = request.getParameter("nom");
				doctor.setLastName(nom); 
				
			} else if (request.getParameter("prenom")!=null) {
				String prenom = request.getParameter("prenom");
				doctor.setFirstName(prenom); 
				
			} else if (request.getParameter("email")!=null) {
				String email = request.getParameter("email");
				doctor.setEmail(email); 
				
			} else if (request.getParameter("tel")!=null) {
				String tel = request.getParameter("tel");
				doctor.setPhone(tel); 
				
			} else if (request.getParameter("adresse")!=null) {
				String adresse = request.getParameter("adresse");
				doctor.setAddress(adresse); 
				
			} else if (request.getParameter("date")!=null) {
				String date = request.getParameter("date");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date_naissance = null; 
				
				try {
					date_naissance = sdf.parse(date); 
				} catch (ParseException e) {
			 
					System.out.println(e.getMessage());
				}
				
				doctor.setBirthDate(date_naissance);
				
			}
			
			doctorService.update(doctor);
			requestDispatcher = request.getRequestDispatcher("infos_patient.jsp");
			request.setAttribute("doctor", doctor);
			requestDispatcher.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
