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
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns ="/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	DoctorService doctorService;
	@EJB
	PatientService patientService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        HttpSession session = request.getSession(true); 
        
        Patient patient = patientService.findByEmail(login);
        
        RequestDispatcher requestDispatcher = null; 
        
        if(patient==null){
            
           Doctor doctor = doctorService.findByEmail(login);
           
           if(doctor==null){
               System.out.println("Erreur de connexion1");
        	   requestDispatcher = request.getRequestDispatcher("index.jsp");
   			   request.setAttribute("error_login", "Login incorrecte");
   			   requestDispatcher.include(request, response);
           
           }else{
        	   	if (doctor.getPassword().equals(password) && !doctor.isDisabled() ) {
        	   		
        	   		System.out.println("Connexion du medecin"); 
        	   		System.out.println("Nom du medecin: "+doctor.getLastName());
        	   		session.setAttribute("doctor", doctor);
        	   		response.sendRedirect("calendar.jsp");
        	   	} else {
        	   		
        	   		System.out.println("Erreur de connexion2");
        	   		requestDispatcher = request.getRequestDispatcher("index.jsp");
        			request.setAttribute("error_login", "Mot de passe incorrecte");
        			requestDispatcher.include(request, response);
        	   		
        	   	}
           }
           
           
        }else{	
         
        	boolean isAdmin=true;
        	Patient admin=null;
        	
        	if (!patient.isAdmin()) {
        		isAdmin=false;
        	}
        	else {
         
        		isAdmin=true;
        		admin=patient;
        	}
            	if (!isAdmin) {
              
            		if (patient.getPassword().equals(password) && !patient.isDisabled()) {
             
            			System.out.println("Connexion du patient"); 
            			session.setAttribute("patient", patient);
            			response.sendRedirect("patient.jsp");
            		}else {
            		 
            			System.out.println("Erreur de connexion3");
            	   		requestDispatcher = request.getRequestDispatcher("index.jsp");
            			request.setAttribute("error_login", "Mot de passe incorrecte");
            			requestDispatcher.include(request, response);
            			
            		}
    				
            	}else {
            	 
            		if (admin.getPassword().equals(password) && !admin.isDisabled()) {
            		 
            			System.out.println("Connexion de l'administrateur ");  
            			session.setAttribute("admin", patient);
            			requestDispatcher = request.getRequestDispatcher("accueil.jsp");
            			request.setAttribute("patient", patient);
    					requestDispatcher.include(request, response);
            		}else {
          
            			System.out.println("Erreur de connexion4");
            	   		requestDispatcher = request.getRequestDispatcher("index.jsp");
            			request.setAttribute("error_login", "Mot de passe incorrecte");
            			requestDispatcher.include(request, response);
            			
            		}
            	}
        }
	}

}
