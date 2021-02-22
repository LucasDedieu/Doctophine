package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.doctophine.model.Patient;
import fr.dauphine.mido.doctophine.service.MailService;
import fr.dauphine.mido.doctophine.service.PatientService;

/**
 * Servlet implementation class ServletPrincipal
 */

@WebServlet(urlPatterns = "/ServletPrincipal")
public class InscriptionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	PatientService patientService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InscriptionController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.getWriter().append("RequestURL : ").append(request.getRequestURI()).append("\nContextPath : ")
		.append(request.getContextPath()).append("\nServ letPath : ").append(request.getServletPath())
		.append("\nMethod : ").append(request.getMethod());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		Patient patient = patientService.findByEmail(email);

		if (patient != null) {

			System.out.println("Erreur");
			requestDispatcher = request.getRequestDispatcher("inscription_client.jsp");
			request.setAttribute("error", "Email deja existant.");
			requestDispatcher.include(request, response);

		} else {

			String adresse = rue + " " + code_postal + " " + ville;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_naissance = null;
			Date creationDate = null;

			try {
				date_naissance = sdf.parse(date);
				creationDate = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
			}

			byte isAdmin = 0;

			Patient newPatient = new Patient(prenom, nom, adresse, email, password, date_naissance, tel);

			patientService.save(newPatient);

			//request.getSession().setAttribute("valide", "Inscription réussie, un email de confirmation vous a été envoyé!");
			//response.sendRedirect("index.jsp");
			/*
			String host="smtp.gmail.com";  
			final String user = "ne.pas.repondre.doctophine@gmail.com";
			final String motdepasse = "JavaApp2021";
			
			String to=email;


			Properties props = new Properties(); 
			props.put("mail.smtp.host", "smtp.gmail.com"); 
			props.put("mail.smtp.auth", "true"); 
			props.put("mail.smtp.port", "587"); 
			props.put("mail.smtp.starttls.enable", "true"); 
			props.put("mail.smtp.ssl.trust", "*");

			Session session = Session.getDefaultInstance(props,  
					new javax.mail.Authenticator() {  
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication(user,motdepasse);  
				}  
			}); 
			*/

			try {  
				String subject = "[Doctophine] Confirmation d'inscription"; 
				String text = "Bonjour\n\nBienvenue sur Docotophine, nous sommes ravi de vous avoir parmi nous. "
						+ "Vous pouvez maintenant vous connecter a votre espace pour prendre et consulter des rendez-vous chez "
						+ "des medecins dans différents centres medicaux de Paris.\n\nL'équipe Doctophine,\n\nParis 16éme."; 
				MailService.getInstance().sendMail(subject, text, email);
				/*
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(user));  
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));  
				
				message.setText("Bonjour\n\nBienvenu sur Docotophine, nous sommes ravi de vous avoir parmi nous. "
						+ "Vous pouvez maintenant vous connecter a votre espace pour prendre et consulter des rendez-vous chez "
						+ "des medecins dans différents centres medicaux de Paris.\n\nL'équipe Doctophine,\n\nParis 16éme.");   

				Transport.send(message);  
				*/
				System.out.println("message sent!");  

				
			} 
			finally {

				//request.getSession().setAttribute("valide", "Inscription réussie, un email de confirmation vous a été envoyé!");
				response.sendRedirect("index.jsp");

			}

		}

	}
}