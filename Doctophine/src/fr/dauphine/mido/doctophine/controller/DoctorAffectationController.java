package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
 * Servlet implementation class DocorAffectationController
 */
@WebServlet("/DoctorAffectationController")
public class DoctorAffectationController extends HttpServlet {
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
	public DoctorAffectationController() {
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

		HttpSession session_ = request.getSession();
		Patient admin = (Patient) session_.getAttribute("admin");
		String doctorEmail = request.getParameter("currentDoctor");
		Doctor newDoctor = (Doctor) doctorService.findByEmail(doctorEmail);
		ArrayList<MedicalCenter> medicalCenters = (ArrayList<MedicalCenter>) medicalCenterService.getAll();
		ArrayList<Speciality> specialities = (ArrayList<Speciality>) specialityService.getAll();
		RequestDispatcher requestDispatcher = null; 
		
		if (request.getParameter("addAffectation") != null) {

			String medicalCenterName = request.getParameter("medicalcenter");
			String specialityName = request.getParameter("speciality");

			if (!medicalCenterName.equals("") && !specialityName.equals("")) {

				MedicalCenter medicalCenter = (MedicalCenter) medicalCenterService
						.findById(Integer.parseInt(medicalCenterName));
				Speciality speciality = (Speciality) specialityService.findById(Integer.parseInt(specialityName));
				System.out.println("doctor name: " + newDoctor.getFullName());
				System.out.println("center medical name: " + medicalCenter.getName());
				System.out.println("speciality name: " + speciality.getName());

				Activity activity = new Activity(newDoctor, medicalCenter, speciality);

				activityService.save(activity); 
				
				requestDispatcher = request.getRequestDispatcher("inscription_doctor_aff.jsp");
				session_.setAttribute("admin", admin);
				request.setAttribute("admin", admin);
				request.setAttribute("newDoctor", newDoctor);
				request.setAttribute("medicalCenters", medicalCenters);
				request.setAttribute("specialities", specialities);
				request.setAttribute("add", "Affectation du medecin au centre medical " + medicalCenter.getName()
						+ " en tant que " + speciality.getName());
				requestDispatcher.include(request, response);

			} else {
				 
				requestDispatcher = request.getRequestDispatcher("inscription_doctor_aff.jsp");
				session_.setAttribute("admin", admin);
				request.setAttribute("admin", admin);
				request.setAttribute("newDoctor", newDoctor);
				request.setAttribute("medicalCenters", medicalCenters);
				request.setAttribute("specialities", specialities);
				requestDispatcher.include(request, response);
			}

		} else {

			String messageConfirmation = "Bonjour,\n\nVotre compte a été crée, on vous invite à vous rendre sur notre site officiel pour vous"
					+ " authentifier avec votre email et votre mot de passe qui est le suivant:" + "\n\n\t "
					+ newDoctor.getPassword() + " \n\n";

			ArrayList<Activity> doctorActivities = (ArrayList<Activity>) activityService
					.findAllByDoctorId(newDoctor.getId());

			if (doctorActivities.size()!=0)
				messageConfirmation += "L'administrateur vous a affectez vers les centres medicaux suivants:\n\n\t";

			for (int i = 0; i < doctorActivities.size(); i++) {

				messageConfirmation += " - " + doctorActivities.get(i).getMedicalCenter().getName()
						+ " en tant que spécialiste en " + doctorActivities.get(i).getSpeciality().getName() + "\n\t";
			}

			messageConfirmation += "\nNB: Veuillez mettre à jour votre mot de passe dans les plus bréf délais\n\n"
					+ "L'équipe Doctophine\n\n" + "Paris 16éme.";

			// Send a confirmation mail

			String host = "smtp.gmail.com";
			final String user = "ne.pas.repondre.doctophine@gmail.com";
			final String motdepasse = "JavaApp2021";

			String to = newDoctor.getEmail();

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", "*");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, motdepasse);
				}
			});

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(user));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("[Doctophine] Confirmation de votre inscription");
				message.setText(messageConfirmation);

				Transport.send(message);

				System.out.println("message sent!");

			} catch (MessagingException mex) {
				System.out.println("Error: unable to send message....");
				mex.printStackTrace();
			} finally {

				requestDispatcher = request.getRequestDispatcher("accueil.jsp");
				request.setAttribute("valide",
						"Inscription reussie, un email de confirmation a ete envoye au nouveau medecin.");
				request.setAttribute("patient", admin);
				requestDispatcher.include(request, response);

			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
