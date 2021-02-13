<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="fr.dauphine.mido.doctophine.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil Doctophine</title>
<link
	href="https://fonts.googleapis.com/css?family=Josefin+Sans&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU"
	crossorigin="anonymous">
</head>
<body>
	<nav
		style="background: #053569; width: 100%; height: 50px; position: absolute; top: 0px;">

		<div
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 15px; left: 10px">
			<h2>Doctophine</h2>
		</div>

		<%
				Patient p = (Patient) request.getAttribute("patient");
				Doctor d = (Doctor) request.getAttribute("doctor");
					
                if (request.getAttribute("doctor")!=null) {
			 %>
		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Medecin</center>

		<% }else if (request.getAttribute("patient")!=null){
            
            	
            %>

		<% if (!p.isAdmin()){ %>

		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Patient</center>

		<% }else{%>

		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Administrateur</center>

		<% }};%>
	</nav>

	<div class="form-signin"
		style="position: absolute; top: 90px; left: 460px;">
		<%
                if (request.getAttribute("valide") != null) {

            %>
		<div class="alert alert-success" role="alert">
			<%= request.getAttribute("valide")%>
		</div>
		<% };%>

	</div>

	<div
		style="color: white; width: 1440px; top: 90px; position: fixed; font-size: 15px; font-family: 'Josefin Sans', sans-serif; left: 1px">
		<ul style="width: 18%; border-radius: 10px; size: 10px; padding: 1px;">
			<li
				style="background: #053569; margin: 1em 0; font-size: 20px; padding-left: 20px; border-radius: 10px">
				<a href="" style="color: white;"> Accueil</a>
			</li>
			<% 	if(request.getAttribute("patient")!=null){
                		if (!p.isAdmin()){ %>
			<li
				style="background: #053569; margin: 1em 0; font-size: 20px; padding-left: 20px; border-radius: 10px">
				<a href="GestionCompteClientController"
				style="color: white;"> Gerer mon compte</a>
			</li>
			<li
				style="background: #053569; margin: 1em 0; font-size: 20px; padding-left: 20px; border-radius: 10px">
				<a
				href="FormulaireInscriptionDoctorController?data=data"
				style="color: white;"> Creer un compte medecin</a>
			</li>
			<% }} else if(request.getAttribute("doctor")!=null){ %>
			<li
				style="background: #053569; margin: 1em 0; font-size: 20px; padding-left: 20px; border-radius: 10px">
				<a href="GestionCompteClientController"
				style="color: white;"> Gerer mon compte</a>
			</li>
			<li
				style="background: #053569; margin: 1em 0; font-size: 20px; padding-left: 20px; border-radius: 10px">
				<a href="" style="color: white;"> Supprimer mon compte</a>
			</li>
			<%}; %>
		</ul>
	</div>


	<form action="Deconnexion" method="GET">
		<button style="position: absolute; left: 1400px; top: 7px;"
			type="submit" class="btn btn-danger">Deconnexion</button>
	</form>

	<div
		style="color: #053569; width: 1440px; top: 60px; position: absolute; font-size: 15px; left: 60px">

		<% if (request.getAttribute("patient")!=null){ 
        		Patient patient = (Patient) request.getAttribute("patient");
        	%>
		<h2 style="font-family: monospace; font-size: 25px"><%=  patient.getFirstName()  %>
			<%=  patient.getLastName()  %></h2>
		<% }else{ 
            	Doctor doctor = (Doctor) request.getAttribute("doctor");
            %>
		<h2 style="font-family: monospace; font-size: 25px""><%=  doctor.getFirstName()  %>
			<%=  doctor.getLastName()  %></h2>
		<% } %>

	</div>
	<div
		style="color: #053569; width: 1440px; top: 60px; position: absolute; font-size: 15px; left: 10px">
		<i class="fa fa-user fa_custom fa-2x"></i>
	</div>


</body>
</html>