<%@page import="java.text.SimpleDateFormat"%>
<%@page import="fr.dauphine.mido.doctophine.model.Availability"%>
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
<%
	controller.init();
%>
<%
	List<Appointment> appointments = controller.getNextAppointments();
%>
<%@ include file="/fragments/header.jspf"%>

<link href="css/patient.css" rel="stylesheet">

<%if(appointments == null || appointments.isEmpty()) {%>
	<h1>Bonjour <%=controller.getLoggedPatient().getFirstName() %>, vous n'avez pas de rendez-vous.</h1>
<%} else{ %>
	<h1>Bonjour <%=controller.getLoggedPatient().getFirstName() %>, voici vos prochains rendez-vous :</h1>
	<%SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM - HH:mm"); %>
	<%for(Appointment app : appointments) {%>
		<div class="card" style="width: 18rem;">
 		 <div class="card-header">
		    <%=sdf.format(app.getStartDate()) %>
		 </div>
		  <ul class="list-group list-group-flush">
		    <li class="list-group-item"><%=app.getActivity().getDoctor() %></li>
		    <li class="list-group-item"><%=app.getActivity().getMedicalCenter() %></li>
		  </ul>
		</div>
		<%} %>
<%} %>

<%@ include file="/fragments/footer.jspf"%>