<%@page import="fr.dauphine.mido.doctophine.model.Availability"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="fr.dauphine.mido.doctophine.model.Appointment"%>
<%@page import="fr.dauphine.mido.doctophine.model.AbstractEvent"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<jsp:useBean id="controller" scope="page"
	class="fr.dauphine.mido.doctophine.controller.CalendarController">
	<%
		
	%><jsp:setProperty name="controller" property="request"
		value="<%=request%>" />
	<%
		
	%><jsp:setProperty name="controller" property="response"
		value="<%=response%>" />
	<%
		
	%><jsp:setProperty name="controller" property="*" />
	<%
		
	%>
</jsp:useBean>
<%
	controller.init();
%>
<%
	List<List<AbstractEvent>> calendar = controller.getCalendar();
%>
<%@ include file="/fragments/header.jspf"%>
<link href="css/calendar.css" rel="stylesheet">

<div class="calendar">


	 <form action="calendar.jsp">
	


		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">Agenda de <%=controller.getLoggedDoctor()%>
				</a>

				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><select name="medicalCenter"
							class="form-select">
								<%
										for (MedicalCenter medicalCenter : controller.getMedicalCenterList()) {
									%>
								<option value="<%=medicalCenter.getId()%>"><%=medicalCenter%></option>
								<%
										}
									%>
						</select></li>
						<li class="nav-item">
							<button name='opPrev' value='true' class="btn btn-default">&lt;</button>
							Semaine <%=controller.getWeek()%>
							<button name='opNext' value='true' class="btn btn-default">&gt;</button>
						</li> 
					</ul>
					<ul class="navbar-nav float-right">
						<li class="nav-item">
							<button name='opEnable' value='true' class="btn btn-default">Activer</button>
							<button name='opDisable' value='true' class="btn btn-default">Desactiver</button>
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
			<%
				for (int slot = 0; slot < 24; slot++) {
			%>
			<tr>
				<td class="hour"><%=8 + (int) (slot / 2)%>h<%=slot % 2 == 0 ? "00" : "30"%>


				</td>
				<%
				int[] days = controller.getDaysOfWeek();
				//for(int i = 0; i < days.length; i++) { 
				//	int day = days[i];
				for(int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; day++) { 
				%>
				<%
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
						
					
						tdAttr = "data-trigger='hover' data-toggle='popover' title=\""+patient+"\" data-content=\""+description+"\"";
					}
							
							
				%>
				<td class="<%=css%> event" <%=tdAttr%>>
					<%if (isAppointment) {%>
					 RDV 
					 <%} else if (isAvailability) {%> 
					  <input type="checkbox" name="slots" value="<%=slot +" "+(day+1)%>"/>Libre 
					 <%} else {%> 
					 <input type="checkbox" name="slots" value="<%=slot+" "+(day+1)%>"/>X
					<%}%>
				</td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
		</table>
		<%--champs caché qui contient current week --%>
		<input type='hidden' name='week' value="<%=controller.getWeek()%>" />
	</form>
</div>
<script>
$(document).ready(function(){
  $('[data-toggle="popover"]').popover();   
});
</script>
<%@ include file="/fragments/footer.jspf"%>