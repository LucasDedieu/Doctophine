<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="fr.dauphine.mido.doctophine.model.*"%>
<%@ page import="fr.dauphine.mido.doctophine.service.*"%>
<%@ page import="java.util.*"%>
<% 
    Doctor currentDoctor = (Doctor) request.getAttribute("newDoctor");
	Patient admin = (Patient) request.getAttribute("admin");
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
		style="color: white; width: 1440px; top: 0px; position: fixed; font-size: 15px; left: 700px">
		<h2>Inscription</h2>
	</div>

	<div class="form-signin"
		style="position: absolute; top: 90px; left: 460px;">
		<%  if (request.getAttribute("error") != null) {  %>

		<div class="alert alert-danger" role="alert">
			<%= request.getAttribute("error")%>
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
					<h3>Informations personnelles</h3>
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
										<option selected><%= currentDoctor.getLastName() %></option> 
								</select>
								</td>
								<td class="col-sm-4">
								<select class="form-control" id="cars" name="medicalcenter">
										<option selected>Choisir un centre medical</option>
										<option value="Dauphine">Dauphine</option>
										<option value="Paul Sabatier">Paul Sabatier</option>
								</select>
								</td>
								<td class="col-sm-4">
								<select class="form-control" id="cars" name="speciality">
										<option selected>Choisir une spécialité</option>
										<option value="Ophtamologie">Ophtamologie</option>
										<option value="ORL">ORL</option>
								</select>
								</td> 
								<td class="col-sm-2"><a class="deleteRow"></a></td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="5" style="text-align: left;"><input
									type="button" class="btn btn-lg btn-block" id="addrow"
									value="Ajouter une autre affectation" /></td>
							</tr>
							<tr>
							</tr>
						</tfoot>
					</table>
				</div>
			</fieldset>
			<button type="submit" id="btn_submit">Submit</button>
		</form>
	</div>
	
	<form action="/Doctophine/Deconnexion" method="GET">
            <button style="position:absolute;left:1380px; top:0px; width:120px" type="submit" class="btn btn-danger">Deconnexion</button>
    </form>

</body>