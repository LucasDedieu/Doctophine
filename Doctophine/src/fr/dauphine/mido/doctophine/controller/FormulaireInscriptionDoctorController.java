package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.dauphine.mido.doctophine.model.Patient;

/**
 * Servlet implementation class FormulaireInscriptionDoctorController
 */
@WebServlet("/FormulaireInscriptionDoctorController")
public class FormulaireInscriptionDoctorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormulaireInscriptionDoctorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		Patient admin = (Patient) session.getAttribute("admin"); 
		RequestDispatcher requestDispatcher = null;
		
		if (admin!=null) {
			
			requestDispatcher = request.getRequestDispatcher("inscription_doctor.jsp");
			request.setAttribute("admin", admin); 
			session.setAttribute("admin", admin);
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
