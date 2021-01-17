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
<form action="calendar.jsp"></form>
<select name="medicalCenter">
<% for(MedicalCenter medicalCenter : controller.getMedicalCenterList()){ %>
  <option value="<%=medicalCenter.getId()%>"><%=medicalCenter%></option>
<% } %>
</select>
<ul>
<% for(Appointment evt : controller.getAbstractEventList()){%>
	<li><%=evt %></li>
<%} %>


</ul>


<%@ include file = "/fragments/footer.jspf"%>