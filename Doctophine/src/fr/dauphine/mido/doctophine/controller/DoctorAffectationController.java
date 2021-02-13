package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
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
		
		String medicalCenterName = request.getParameter("medicalcenter");
		String specialityName = request.getParameter("speciality");
		String doctorEmail = request.getParameter("currentDoctor");

		Doctor newDoctor = (Doctor) doctorService.findByEmail(doctorEmail);
		
		HashMap<MedicalCenter,Speciality> hm = new HashMap<MedicalCenter,Speciality>();

		MedicalCenter medicalCenter = (MedicalCenter) medicalCenterService.findByName(medicalCenterName);
		Speciality speciality = (Speciality) specialityService.findByName(specialityName);
		hm.put(medicalCenter, speciality);
		
		Activity activity = new Activity(newDoctor, medicalCenter, speciality);

		activityService.save(activity);
		 
		int i = 0;
		while (request.getParameter("medicalcenter" + i) != null && request.getParameter("speciality" + i) != null) {

			String medicalCenterName1 = request.getParameter("medicalcenter" + i);
			String specialityName1 = request.getParameter("speciality" + i);

			MedicalCenter medicalCenter1 = (MedicalCenter) medicalCenterService.findByName(medicalCenterName1);
			Speciality speciality1 = (Speciality) specialityService.findByName(specialityName1);
			hm.put(medicalCenter1, speciality1);
			
			Activity activity1 = new Activity(newDoctor, medicalCenter1, speciality1);
			
			 
			activityService.save(activity1);
			i++;
		}
		
		String messageConfirmation = "Bonjour,\n\nVotre compte a été crée, on vous invite à vous rendre sur notre site officiel pour vous"
				+ " authentifier avec votre email et votre mot de passe qui est le suivant:"
				+ "\n\n\t "+ newDoctor.getPassword() +" \n\n"
				+ "L'administrateur vous a affectez vers le(s) centre(s) medica(ux)l suivant(s):\n\n\t";
		 
		for(Map.Entry element: hm.entrySet()) {
			 
			MedicalCenter mc = (MedicalCenter) element.getKey();
			Speciality s = (Speciality) element.getValue();
			messageConfirmation+=" - "+ mc.getName()+" en tant que spécialiste en "+s.getName()+"\n\t";
		}
		
		messageConfirmation += "\nNB: Veuillez mettre à jour votre mot de passe dans les plus bréf délais\n\n"
				+ "L'équipe Doctophine\n\n" + "Paris 16éme.";
		
		RequestDispatcher requestDispatcher = null;
		// Send a confirmation mail

		String host = "smtp.gmail.com";
		final String user = "ne.pas.repondre.doctophine@gmail.com";
		final String motdepasse = "JavaApp2021";

		String to = "anis.si-youcef@dauphine.eu";

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
			request.setAttribute("valide", "Inscription reussie, un email de confirmation a ete envoye au nouveau medecin.");
			request.setAttribute("patient", admin);
			requestDispatcher.include(request, response);

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
