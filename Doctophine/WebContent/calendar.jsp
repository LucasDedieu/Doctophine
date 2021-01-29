<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="fr.dauphine.mido.doctophine.model.Appointment"%>
<%@page import="fr.dauphine.mido.doctophine.model.AbstractEvent"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.CalendarController"><%
%><jsp:setProperty name="controller" property="request"       value="<%= request %>"/><%
%><jsp:setProperty name="controller" property="response"      value="<%= response %>"/><%
%><jsp:setProperty name="controller" property="*" /><%
%></jsp:useBean>
<%controller.init(); %>
<%@ include file = "/fragments/header.jspf"%>
<h1>Agenda de <%=controller.getLoggedDoctor()%></h1>
<h2>Semaine <%=controller.getWeek() %></h2>
<form action="calendar.jsp">
	<select name="medicalCenter">
	<% for(MedicalCenter medicalCenter : controller.getMedicalCenterList()){ %>
	  <option value="<%=medicalCenter.getId()%>"><%=medicalCenter%></option>
	<% } %>
	</select>
	<button name='opPrev' value='true'>&lt;</button>
	<button name='opNext' value='true'>&gt;</button>
	<input type='hidden' name='week' value="<%= controller.getWeek()%>"/>
</form>

<ul>
<%
	for(Appointment evt : controller.getAppointmentList()){
%>
	<li><%=evt %></li>
<%} %>


</ul>


<%  List<List<AbstractEvent>> calendar = controller.getCalendar(); %>

<table>
	<tr>
	    <th></th>
		<th>Lundi</th>
		<th>Mardi</th>
		<th>Mercredi</th>
		<th>Jeudi</th>
		<th>Vendredi</th>
		<th>Samedi</th>
		<th>Dimanche</th>
		
	</tr>
<% for(int slot=0; slot<24; slot++) { %>
	<tr>
	   <td>
	   <%= 8 + (int)(slot/2) %>h<%= slot%2==0 ? "00":"30" %>
	   
	   
	   </td>
		<%  for(int day=Calendar.SUNDAY; day<=Calendar.SATURDAY;day++) { %>
			<% AbstractEvent event = calendar.get(day-1).get(slot); %>
			<td>
				<%if(event !=null) {%>
				Occupé
				<%}else{%>
				Libre
				<%} %>
			</td>
		<%}%>
	</tr>
<%}%>
</table>
<%@ include file = "/fragments/footer.jspf"%>