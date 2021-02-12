<%@page import="fr.dauphine.mido.doctophine.model.Activity"%>
<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<%@page import="fr.dauphine.mido.doctophine.model.Availability"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="fr.dauphine.mido.doctophine.model.Appointment"%>
<%@page import="fr.dauphine.mido.doctophine.model.AbstractEvent"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.MakeAppointmentController">
	<jsp:setProperty name="controller" property="request" value="<%=request%>" />
	<jsp:setProperty name="controller" property="response" value="<%=response%>" />
	<jsp:setProperty name="controller" property="*" />
</jsp:useBean>

<%controller.init();%>

<%
List<List<AbstractEvent>> calendar = controller.getCalendar();
Activity activity = controller.getCurrentActivity(); 
Doctor doctor = activity.getDoctor(); 
%>



<%@ include file="/fragments/header.jspf"%>
<link href="css/makeAppointment.css" rel="stylesheet">


<div class="calendar">
	 <form action="makeAppointment.jsp">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">Disponibilités de <%=doctor%></a>
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item week">
							<button name='opPrev' value='true' class="btn btn-link btn-light btn-week"><span class="icon icon-backward2"></span></button>
							Semaine <%=controller.getWeek()%>
							<button name='opNext' value='true' class="btn btn-link btn-light btn-week"><span class="icon icon-forward3"></span></button>
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

				<%} %>
			</tr>
			<% for(int slot = 0; slot < 24; slot++){ %>
					<tr>
						<td class="hour"><%=8 + (int) (slot / 2)%>h<%=slot % 2 == 0 ? "00" : "30"%>
		
		
						</td>
						<%
						int[] days = controller.getDaysOfWeek();
						//for(int i = 0; i < days.length; i++) { 
						//	int day = days[i];
						for(int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; day++) { %>
						<%
							AbstractEvent event = calendar.get(day-1).get(slot);
							boolean isAvailability = event instanceof Availability;
							String css = isAvailability ? "availability" : "";			
						%>
						
							<td class="<%=css%> event" >
								<%if (isAvailability) {%> 
								  <a class="btn btn-secondary btn-appointment" href="makeAppointment.jsp?opSelect=true&activity=<%=activity.getId()%>&slot=<%=slot +" "+(day+1)%>&year=<%=controller.getYear() %>&week=<%=controller.getWeek()%>">Libre</a>
								 <%} else {%> 
								 <span>-</span>
								<%}%>
							</td>
						<% } %>
					</tr>
				<% }%>
			</table>
		<%--champs caché qui contient current week --%>
		<input type='hidden' name='week' value="<%=controller.getWeek()%>" />
		<%--champs caché qui contient l'activity --%>
		<input type='hidden' name='activity' value="<%=activity.getId()%>" />
	</form>
</div>
<script src="js/makeAppointment.js"></script>

<%@ include file="/fragments/footer.jspf"%>