package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeconnexionController
 */
@WebServlet("/Deconnexion")
public class DeconnexionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeconnexionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		HttpSession session = request.getSession();
        
        if(session.getAttribute("patient")!=null)
            session.removeAttribute("patient");
        else if(session.getAttribute("doctor")!=null)
            session.removeAttribute("doctor");
        else
        	session.removeAttribute("admin");
        
        response.sendRedirect("index.jsp");
        
        
	}



}
