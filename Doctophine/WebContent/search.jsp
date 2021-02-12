<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<%@page import="fr.dauphine.mido.doctophine.model.Activity"%>
<%@page import="fr.dauphine.mido.doctophine.model.Speciality"%>
<%@page import="java.util.List"%>
<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.SearchController">
	<jsp:setProperty name="controller" property="request" value="<%=request%>" />
	<jsp:setProperty name="controller" property="response" value="<%=response%>" />
	<jsp:setProperty name="controller" property="*" />
</jsp:useBean>
<%controller.init();%>
<% List<Activity> activities = controller.getActivityList();%>


<%@ include file="/fragments/header.jspf"%>
<link href="css/search.css" rel="stylesheet">


<div class="search">
	<%@ include file="/fragments/searchForm.jspf"%>
	 



	<%if(activities.isEmpty()) {%>
		<div class="no-result">Nous n'avons trouvé aucun professionnel de santé correspondant à votre requete...</div>
	<%}else{ %>
		<div class="cards">
			<%for(Activity activity: activities){%>
				<div class="card card-doctor">
					 <div class="card-body">
					 	<div class="card-photo">
					 		<img alt="photo" class="rounded-circle" src="<%=activity.getDoctor().getPhoto()%>"/>
					 	</div>
					 	<div class="card-info">
						 	<h5 class="card-title"><%=activity.getDoctor().getFullName() %></h5>
						 	<h6 class="card-subtitle"><%=activity.getSpeciality().getName() %></h6>
						 	<p class="card-text">
						 		<%=activity.getMedicalCenter().getName() %> <br/>
						 		<span class="icon icon-location"></span>
					    		<a target="_blank" href="<%=activity.getMedicalCenter().getMapsURL()%>"><%=activity.getMedicalCenter().getAddress() %> </a>
						 	</p>
						 	<a href="makeAppointment.jsp?activity=<%=activity.getId() %>" class="card-link">Prendre un rendez-vous</a>	 
						</div>
					 </div>
				</div>
	
			<%} %>
		</div>
	<%}%>
</div>