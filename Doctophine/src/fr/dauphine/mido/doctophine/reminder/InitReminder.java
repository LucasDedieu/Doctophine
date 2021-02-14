package fr.dauphine.mido.doctophine.reminder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup=1)
public class InitReminder extends HttpServlet{
	 
	@Override
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		Reminder reminder = new Reminder(3,0);//on envoie les rappels à 7h00 du matin
	}
	
}
