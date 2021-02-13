<%@page import="java.util.Date"%>
<%@page import="fr.dauphine.mido.doctophine.model.Availability"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="fr.dauphine.mido.doctophine.model.Appointment"%>
<%@page import="fr.dauphine.mido.doctophine.model.AbstractEvent"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.CalendarController">
	<jsp:setProperty name="controller" property="request" value="<%=request%>" />
	<jsp:setProperty name="controller" property="response" value="<%=response%>" />
	<jsp:setProperty name="controller" property="*" />
</jsp:useBean>

<%controller.init();%>

<%List<List<AbstractEvent>> calendar = controller.getCalendar();%>
<%MedicalCenter currentMedicalCenter = controller.getCurrentMedicalCenter(); %>
<%request.setAttribute("logoLink", "calendar.jsp"); %>
<%Date today = new Date(); %>




<%@ include file="/fragments/header.jspf"%>
<link href="css/calendar.css" rel="stylesheet">
<link rel="stylesheet" href="css/tablecellsselection.css">


<div class="calendar">

	 <form action="calendar.jsp">
	
	
	


		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">Agenda de <%=controller.getLoggedDoctor()%>
				
				</a>

				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item">
						<select name="medicalCenter" class="form-select form-control medical-center-chooser">
								<%for (MedicalCenter medicalCenter : controller.getMedicalCenterList()) {%>
									<option value="<%=medicalCenter.getId()%>"  <%=medicalCenter.getId()==currentMedicalCenter.getId()?"selected":"" %>><%=medicalCenter%></option>
								<%}%>
						</select></li>
						<li class="nav-item week">
							<button name='opPrev' value='true' class="btn btn-link btn-light btn-week"><span class="icon icon-backward2"></span></button>
							Semaine <%=controller.getWeek()%>
							<button name='opNext' value='true' class="btn btn-link btn-light btn-week"><span class="icon icon-forward3"></span></button>
						</li> 
					</ul>
					<ul class="navbar-nav float-right ml-auto">
						<li class="nav-item">
							<%--<button name='opEnable' value='true' class="btn btn-default enable-btn">Activer</button>
							<button name='opDisable' value='true' class="btn btn-default disable-btn">Desactiver</button> --%>
							<a href="#" class="btn btn-outline-success enable-btn"><span class="icon icon-checkmark"></span> Activer</a>
							<a href="#" class="btn btn-outline-danger disable-btn"><span class="icon icon-cross"></span> Desactiver</a>
						</li>
					</ul>
					
				</div>
			</div>
		</nav>




		<table class="table">
			<tr class="day">
				<th></th>
				
				<% for(String dayName : controller.getDayNames()){ %>				
					<th><%=dayName%></th>
				<%}%>
			</tr>
			<%for (int slot = 0; slot < 24; slot++){ %>
			<tr>
				<td class="hour"><%=8 + (int) (slot / 2)%>h<%=slot % 2 == 0 ? "00" : "30"%>
	
	
				</td>
				<%
				int[] days = controller.getDaysOfWeek();
				//for(int i = 0; i < days.length; i++) { 
				//	int day = days[i];
				for(int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; day++) { 
					AbstractEvent event = calendar.get(day-1).get(slot);
					boolean isAppointment = event instanceof Appointment;
					boolean isAvailability = event instanceof Availability;
					boolean isEmpty = event == null;
					String css = isAppointment ? "appointment" : (isAvailability ? "availability" : "empty");
					String tdAttr = "";
					if(isAppointment){
						Appointment appointment = (Appointment)event;
						String patient = appointment.getPatient().getFullName();
						String description = appointment.getDescription();
						if(description==null){
							description="";
						}
					
						tdAttr = "data-trigger='hover' data-toggle='popover' title=\""+patient+"\" data-content=\""+description+"\"";
					}
							
							
					%>
					<td class="<%=css%> event" <%=tdAttr%>>
						<%if (isAppointment) {%>
							 RDV 
							 <%Appointment appointment = (Appointment)event; %>
							 <%if(appointment.getStartDate().after(today)){ %>
							 	<a href="cancelAppointment.jsp?id=<%=appointment.getId()%>&from=doctor" onClick="return confirm('Voulez-vous vraiment annuler ce rendez-vous ?')" title="Supprimer...">&times;</a>
							 <%} %>
						 <%}else if (isAvailability) {%> 
						  	<input type="checkbox" name="slots" value="<%=slot +" "+(day+1)%>"/><span class="icon icon-checkmark"></span> 
						 <%} else {%> 
						 	<input type="checkbox" name="slots" value="<%=slot+" "+(day+1)%>"/><span class="icon icon-cross"></span> 
						 <%}%>
					</td>
				<%}%>
			</tr>
			<%}%>
		</table>
		<%--champs caché qui contient current week --%>
		<input type='hidden' name='week' value="<%=controller.getWeek()%>" />
		<%--champs caché qui contient l'année --%>
		<input type='hidden' name='year' value="<%=controller.getYear()%>" />
	</form>
</div>
<script src="js/tablecellsselection.js"></script>
<script src="js/calendar.js"></script>

<%@ include file="/fragments/footer.jspf"%>