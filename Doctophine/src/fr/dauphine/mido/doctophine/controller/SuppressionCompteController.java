package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import fr.dauphine.mido.doctophine.model.Appointment;
import fr.dauphine.mido.doctophine.model.Doctor;
import fr.dauphine.mido.doctophine.model.MedicalCenter;
import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.model.Speciality;
import fr.dauphine.mido.doctophine.service.ActivityService;
import fr.dauphine.mido.doctophine.service.AppointmentService;
import fr.dauphine.mido.doctophine.service.DoctorService;
import fr.dauphine.mido.doctophine.service.PatientService;

/**
 * Servlet implementation class SuppressionCompteController
 */
@WebServlet("/SuppressionCompte")
public class SuppressionCompteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	PatientService patientService;
	@EJB
	AppointmentService appointmentService;
	@EJB
	DoctorService doctorService;
	@EJB
	ActivityService activityService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SuppressionCompteController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session_ = request.getSession();
		Patient patient = (Patient) session_.getAttribute("patient");
		Doctor doctor = (Doctor) session_.getAttribute("doctor");
		RequestDispatcher requestDispatcher = null;
		ArrayList<Appointment> allAppointment = new ArrayList<Appointment>(); 
		
		if (patient != null) {

			patient.setStatus("Inactif");
			patient.setDisabled(true);
			patientService.update(patient);
			allAppointment = (ArrayList<Appointment>) appointmentService.findAllByPatientId(patient.getId());

			ArrayList<Activity> activities = new ArrayList<Activity>();
			ArrayList<Doctor> doctors = new ArrayList<Doctor>();
			ArrayList<Speciality> specialities = new ArrayList<Speciality>();
			ArrayList<MedicalCenter> medicalcenters = new ArrayList<MedicalCenter>();

			String messageConfirmation = "Bonjour,\n\nVotre compte a été supprimé, "
					+ "\n\nles rendez-vous suivants ont été annulés:\n\n";

			for (int i = 0; i < allAppointment.size(); i++) {
				allAppointment.get(i).setCancelled(true);
				appointmentService.update(allAppointment.get(i));
				activities.add(allAppointment.get(i).getActivity());
			}

			for (int j = 0; j < activities.size(); j++) {
				System.out.println("Doctor name: " + activities.get(j).getDoctor().getLastName());
				doctors.add(activities.get(j).getDoctor());
				specialities.add(activities.get(j).getSpeciality());
				medicalcenters.add(activities.get(j).getMedicalCenter());

				int mois = allAppointment.get(j).getStartDate().getMonth() + 1;
				int année = allAppointment.get(j).getStartDate().getYear() + 1900;
				messageConfirmation += "\t\t- Le " + allAppointment.get(j).getStartDate().getDate() + "-" + mois + "-"
						+ année + " à " + allAppointment.get(j).getStartDate().getHours() + "h"
						+ allAppointment.get(j).getStartDate().getMinutes();

				messageConfirmation += ", chez le medecin " + activities.get(j).getDoctor().getLastName()
						+ " spécialiste en " + activities.get(j).getSpeciality().getName() + " au centre médical "
						+ activities.get(j).getMedicalCenter().getName() + ".\n\t\t- Téléphone du centre medical: "
						+ activities.get(j).getMedicalCenter().getPhone() + "\n\t\t- Adresse du centre medical: "
						+ activities.get(j).getMedicalCenter().getAddress() + "\n\n";
			}

			messageConfirmation += "L'équipe Doctophine\n\n" + "Paris 16éme.";

			String host = "smtp.gmail.com";
			final String user = "ne.pas.repondre.doctophine@gmail.com";
			final String motdepasse = "JavaApp2021";

			String to = patient.getEmail();

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
				message.setSubject("[Doctophine] Confirmation de suppression de votre compte");
				message.setText(messageConfirmation);

				Transport.send(message);

				System.out.println("message sent!");

			} catch (MessagingException mex) {
				System.out.println("Error: unable to send message....");
				mex.printStackTrace();
			}

			requestDispatcher = request.getRequestDispatcher("index.jsp");
			session_.removeAttribute("patient");
			requestDispatcher.include(request, response);

		} else if (doctor != null) {

			ArrayList<Activity> activities = new ArrayList<Activity>();
			 
			activities = (ArrayList<Activity>) activityService.findAllByDoctorId(doctor.getId());
			boolean noActivity = true;

			for (int i = 0; i < activities.size(); i++) {
				if (!appointmentService.findAllByActivityId(activities.get(i).getId()).isEmpty()) {
					noActivity = false;
					break;
				}
			}

			// Il y a des rdv en cours
			if (!noActivity) {
				System.out.println("noActivity");
				requestDispatcher = request.getRequestDispatcher("infos_patient.jsp");
				request.setAttribute("doctor", doctor);
				request.setAttribute("error_suppression",
						"Votre compte ne peut pas etre supprime, il y a des RDV a venir.");
				requestDispatcher.include(request, response);

			} else {
				// Pas de rdv en cours, suppression possible 
				 
				doctor.setStatus("inactif");
				doctor.setDisabled(true);
				doctorService.update(doctor);
				
				final String user = "ne.pas.repondre.doctophine@gmail.com";
				final String motdepasse = "JavaApp2021";

				String to = doctor.getEmail();
				
				String messageConfirmation = "Bonjour,\n\nVotre compte a été supprimé\n\n"+
									"L'équipe Doctophine\n\n" + "Paris 16éme.";
				
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
					message.setSubject("[Doctophine] Confirmation de suppression de votre compte");
					message.setText(messageConfirmation);

					Transport.send(message);

					System.out.println("message sent!");

				} catch (MessagingException mex) {
					System.out.println("Error: unable to send message....");
					mex.printStackTrace();
				}
				
				
				requestDispatcher = request.getRequestDispatcher("index.jsp");
				session_.removeAttribute("doctor");
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
