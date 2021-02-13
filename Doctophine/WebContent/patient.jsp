<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.dauphine.mido.doctophine.model.Availability"%>
<%@page import="fr.dauphine.mido.doctophine.model.Speciality"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="fr.dauphine.mido.doctophine.model.Appointment"%>
<%@page import="fr.dauphine.mido.doctophine.model.AbstractEvent"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.PatientController">
	<jsp:setProperty name="controller" property="request" value="<%=request%>" />
	<jsp:setProperty name="controller" property="response" value="<%=response%>" />
	<jsp:setProperty name="controller" property="*" />
</jsp:useBean>

<%controller.init();%>

<%List<Appointment> nextAppointments = controller.getNextAppointments();%>
<%List<Appointment> previousAppointments = controller.getPreviousAppointments();%>

<%request.setAttribute("logoLink", "patient.jsp"); %>


<%@ include file="/fragments/header.jspf"%>


<link href="css/patient.css" rel="stylesheet">  








<div class="patient">
	<h1>Bonjour <%=controller.getLoggedPatient().getFirstName() %> !</h1>
	
	
	<%@ include file="/fragments/searchForm.jspf"%>
	
	
	
	
	
	
	
	<%if(nextAppointments == null || nextAppointments.isEmpty()) {%>
		<h2>Vous n'avez pas de rendez-vous.</h2>
	<%} else{ %>
		<div class="next-appointments">
			<h2>Voici vos prochains rendez-vous :</h2>
			<div class="cards">
			<%SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM - HH:mm"); %>
			<%for(Appointment app : nextAppointments) {%>
				<%Doctor doctor = app.getActivity().getDoctor();
				MedicalCenter medicalCenter = app.getActivity().getMedicalCenter();%>
				<div class="card" style="width: 18rem;">
		 		 <div class="card-header">
		 		 	<span class="icon icon-calendar"></span>
				    <%=sdf.format(app.getStartDate()) %>
				 </div>
				  <ul class="list-group list-group-flush">
				  
				    <li class="list-group-item">
				    	<div class="photo">
				 			<img alt="photo" class="rounded-circle" src="<%=doctor.getPhoto()%>"/>
				 		</div>	
				 		<div class="info">
					    	<div class="doctor-name"><%=doctor %></div>
					   	 	<div class="doctor-speciality"><%=app.getActivity().getSpeciality().getName() %></div>
					    </div>
				    </li>
				    <li class="list-group-item">
				    	<div class="mc-name"><%=medicalCenter %></div>
				    	<div class="mc-address">
				    		<span class="icon icon-location"></span>
				    		<a target="_blank" href="<%=medicalCenter.getMapsURL()%>"><%=medicalCenter.getAddress() %> </a>
				    	</div>
				    	<div class="mc-phone">
				    		<span class="icon icon-phone"></span>
				    	
				    		<%=medicalCenter.getPhone() %>
				    	</div>   		
				    </li>
				    <li class="list-group-item">
				    	<div class="cancel-button">
				    		<a href="cancelAppointment.jsp?id=<%=app.getId()%>&from=patient" onClick="return confirm('Voulez-vous vraiment annuler ce rendez-vous ?')" class="btn btn-outline-light btn-sm">Annuler</a>
				    	</div>
				    </li>
				  </ul>
				</div>
				<%} %>
			</div>
		</div>
	<%} %>
	<%if(previousAppointments!=null){ %>
		<div class="previous-appointments">
				<h2>Vos anciens rendez-vous :</h2>
				<div class="cards">
				<%SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM - HH:mm"); %>
				<%for(Appointment app : previousAppointments) {%>
					<%Doctor doctor = app.getActivity().getDoctor();
					MedicalCenter medicalCenter = app.getActivity().getMedicalCenter();%>
					<div class="card" style="width: 18rem;">
			 		 <div class="card-header">
					    <span class="icon icon-calendar"></span>
					    <%=sdf.format(app.getStartDate()) %>
					 </div>
					  <ul class="list-group list-group-flush">
					    <li class="list-group-item">
						    <div class="photo">
					 			<img alt="photo" class="rounded-circle" src="<%=doctor.getPhoto()%>"/>
					 		</div>	
					 		<div class="info">
						    	<div class="doctor-name"><%=doctor %></div>
						   	 	<div class="doctor-speciality"><%=app.getActivity().getSpeciality().getName() %></div>
						    </div>
					    </li>
					    <li class="list-group-item">
					    	<div class="mc-name"><%=medicalCenter %></div>
					    	<div class="mc-address">
					    		<span class="icon icon-location"></span>
					    		<a target="_blank" href="<%=medicalCenter.getMapsURL()%>"><%=medicalCenter.getAddress() %> </a></div>
					    	<div class="mc-phone">
					    		<span class="icon icon-phone"></span>
					    		<%=medicalCenter.getPhone() %>
					    	</div>   		
					    </li>
					  </ul>
					</div>
					<%} %>
				</div>
			</div>
		<%} %>
</div>
