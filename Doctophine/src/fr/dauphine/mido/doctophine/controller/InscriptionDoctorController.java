package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.doctophine.model.Activity;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.ActivityService;
import fr.dauphine.mido.doctophine.service.DoctorService;
import fr.dauphine.mido.doctophine.service.MedicalCenterService;
import fr.dauphine.mido.doctophine.service.SpecialityService;

/**
 * Servlet implementation class InscriptionDoctorController
 */
@WebServlet("/InscriptionDoctorController")
public class InscriptionDoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	MedicalCenterService medicalCenterService;
	@EJB
	SpecialityService specialityService;
	@EJB
	DoctorService doctorService;
	@EJB
	ActivityService activityService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionDoctorController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		HttpSession session = request.getSession(false);
		Patient admin = (Patient) session.getAttribute("admin");
		System.out.println("Name: "+admin.getFirstName());
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String date = request.getParameter("date");
		String tel = request.getParameter("tel");
		String rue = request.getParameter("rue");
		String code_postal = request.getParameter("code_postal");
		String ville = request.getParameter("ville");

		RequestDispatcher requestDispatcher = null;

		Doctor doctor = doctorService.findByEmail(email);
		
		if (doctor != null) {
			 
			System.out.println("Erreur");
			requestDispatcher = request.getRequestDispatcher("inscription_doctor.jsp");
			session.setAttribute("admin", admin);
			request.setAttribute("admin", admin);
			request.setAttribute("error", "Email deja existant.");
			requestDispatcher.include(request, response);

		} else {

			String adresse = rue + " " + code_postal + " " + ville;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_naissance = null; 

			try {
				date_naissance = sdf.parse(date); 
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}
 

			Doctor newDoctor = new Doctor(prenom, nom, adresse, email, password, date_naissance, tel); 
			
			doctorService.save(newDoctor); 
			
			ArrayList<MedicalCenter> medicalCenters = (ArrayList<MedicalCenter>) medicalCenterService.getAll();
			ArrayList<Speciality> specialities = (ArrayList<Speciality>) specialityService.getAll();

			requestDispatcher = request.getRequestDispatcher("inscription_doctor_aff.jsp");
			session.setAttribute("admin", admin);
			request.setAttribute("admin", admin);
			request.setAttribute("newDoctor", newDoctor);
			request.setAttribute("medicalCenters", medicalCenters);
			request.setAttribute("specialities", specialities);
			response.setCharacterEncoding("UTF-8");
			requestDispatcher.include(request, response);

		}
		
	}
	
}
