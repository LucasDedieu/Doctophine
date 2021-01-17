package fr.dauphine.mido.doctophine.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractController {
	protected HttpServletRequest request;
	protected HttpServletResponse response;

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
}
