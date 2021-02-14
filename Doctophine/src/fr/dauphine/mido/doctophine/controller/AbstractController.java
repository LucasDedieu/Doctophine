package fr.dauphine.mido.doctophine.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.dauphine.mido.doctophine.model.AbstractAccount;

public class AbstractController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected AbstractAccount loggedAccount; 

	public void setRequest(HttpServletRequest request) {
		if (this.request != request) {
			this.request = request;
		}
	}

	public void setResponse(HttpServletResponse response) {
		if (this.response != response) {
			this.response = response;
		}
	}
	
	public void init() throws IOException {
		loggedAccount = (AbstractAccount) request.getSession().getAttribute("patient");
		if(loggedAccount==null) {
			loggedAccount=(AbstractAccount) request.getSession().getAttribute("doctor");
			if(loggedAccount==null) {
				response.sendRedirect("index.jsp");
			}
		}
	}

}
