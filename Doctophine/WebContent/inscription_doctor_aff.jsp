<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="fr.dauphine.mido.doctophine.model.*"%>
<%@ page import="fr.dauphine.mido.doctophine.service.*"%>
<%@ page import="java.util.*"%>
<% 
    Doctor currentDoctor = (Doctor) request.getAttribute("newDoctor");
	Patient admin = (Patient) request.getAttribute("admin");
	ArrayList<MedicalCenter> medicalCenters = (ArrayList<MedicalCenter>) request.getAttribute("medicalCenters");
	ArrayList<Speciality> specialities = (ArrayList<Speciality>) request.getAttribute("specialities");
	
%>

<!DOCTYPE html>
<html>
<head>
<title>Formulaire d'inscription</title>
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600'
	rel='stylesheet' type='text/css'>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"
	rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="css/inscription_doctor.css">
<script src="js/inscription_doctor.js"></script>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
	<nav
		style="background: #053569; width: 100%; height: 50px; position: absolute; top: 0px;">
	</nav>
	<div
		style="color: white; width: 1440px; top: 0px; position: fixed; font-size: 15px; left: 10px">
		<h2>Doctophine</h2>
	</div>
	<div
		style="color: white; width: 1440px; top: 0px; position: fixed; font-size: 15px; left: 600px">
		<h2>Inscription de medecin</h2>
	</div>
 
	
	<div style="position: absolute; top: 70px; left: 500px;">
		<%
                if (request.getAttribute("add") != null) {

            %>
		<div
			style="position:relative;padding:.75rem 1.25rem;margin-bottom:1rem;border:1px solid transparent;border-radius:.25rem;color:#155724;background-color:#d4edda;border-color:#c3e6cb;border-top-color:#b1dfbb;"
			role="alert">
			<%= request.getAttribute("add")%>
		</div>
		<% };%>

	</div>
	
	<div
		style="color: #053569; width: 1440px; top: 43px; position: absolute; font-size: 15px; left: 60px">
		<h2 style="font-family: monospace; font-size: 25px"><%=admin.getFirstName()%>
			<%=admin.getLastName()%></h2>
	</div>

	<div
		style="color: #053569; width: 1440px; top: 60px; position: absolute; font-size: 15px; left: 10px">
		<i class="fa fa-user fa_custom fa-2x"></i>
	</div>

	<div class="main-block1">
		<form method="Post" action="DoctorAffectationController">
			<input type="hidden" name="currentDoctor" value=<%= currentDoctor.getEmail() %> />
			<fieldset>
				<legend>
					<h3>Affectation du nouveau medecin</h3>
				</legend>
				<div class="container">
					<table id="myTable" class=" table order-list">
						<thead>
							<tr>
								<td style="padding-left: 10px;">Nom du medecin</td>
								<td style="padding-left: 10px;">Centre médical</td>
								<td style="padding-left: 10px;">Spécialité</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="col-sm-4">
								<select class="form-control" id="cars" >
										<option selected><%= currentDoctor.getFullName() %></option> 
								</select>
								</td>
								<td class="col-sm-4">
								<select class="form-control" id="cars" name="medicalcenter"  >
										<option value="" selected>Choisir un centre medical</option>
										<% for(int i=0; i<medicalCenters.size(); i++){ %>
										<option value=<%=medicalCenters.get(i).getId() %> ><%=medicalCenters.get(i).getName() %></option>
										<% } %>
								</select>
								</td>
								<td class="col-sm-4">
								<select class="form-control" id="cars" name="speciality"  >
										<option value="" selected>Choisir une spécialité</option> 
										<% for(int i=0; i<specialities.size(); i++){ %>
										<option value=<%=specialities.get(i).getId() %> ><%=specialities.get(i).getName() %></option>
										<% } %>
								</select>
								</td> 
								<td class="col-sm-2"><a class="deleteRow"></a></td>
							</tr>
						</tbody> 
					</table>
				</div>
			</fieldset>
			<button type="submit" name="addAffectation" id="btn_submit">Ajouter une affectation</button>
			<button type="submit" name="confirme" id="btn_submit">Submit</button> 
		</form>
	</div>
	
	<form action="Deconnexion" method="GET">
            <button style="position:absolute;left:1380px; top:0px; width:120px" type="submit" class="btn btn-danger">Deconnexion</button>
    </form>

</body>