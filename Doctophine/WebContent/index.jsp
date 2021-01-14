<!doctype html>
<%@page import="fr.dauphine.mido.doctophine.model.Activity"%>
<%@page import="fr.dauphine.mido.doctophine.model.Speciality"%>
<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<%@page import="fr.dauphine.mido.doctophine.model.Patient"%>
<%@page import="java.util.List"%>
<%@page import="fr.dauphine.mido.doctophine.model.MedicalCenter"%>
<%@page import="fr.dauphine.mido.doctophine.service.DoctophineService"%>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Doctophine</title>
  </head>
  <body>
  
  
    <h1>Liste de centre medicaux</h1>
  	<% List<MedicalCenter> medicalCenterList = DoctophineService.getInstance().getMedicalCenterList(); %>
  	<ul>
  	<% for(MedicalCenter medicalCenter : medicalCenterList) { %>
  	  <li><%= medicalCenter.getName() %></li>
	<% } %>
	</ul>
	
	
	
	
	<h1>Liste des patients</h1>
  	<% List<Patient> patientList = DoctophineService.getInstance().getPatientList(); %>
  	<ul>
  	<% for(Patient patient : patientList) { %>
  	  <li><%= patient.getFirstName() %></li>
	<% } %>
	</ul>
	
	
	
	
	<h1>Liste des docteurs</h1>
  	<% List<Doctor> doctorList = DoctophineService.getInstance().getDoctorList(); %>
  	<ul>
  	<% for(Doctor doctor : doctorList) { %>
  	  <li><%= doctor.getFirstName() %></li>
	<% } %>
	</ul>
	
	
	
	
	
	 <h1>Liste des specialités</h1>
  	<% List<Speciality> specialityList = DoctophineService.getInstance().getSpecialityList(); %>
  	<ul>
  	<% for(Speciality speciality : specialityList) { %>
  	  <li><%= speciality.getName() %></li>
	<% } %>
	</ul>
	
	
	
	<h1>Liste des activités</h1>
  	<% List<Activity> activityList = DoctophineService.getInstance().getActivityList(); %>
  	<ul>
  	<% for(Activity activity : activityList) { %>
  	  <li><%= activity.getDoctor().getFirstName() %>-<%= activity.getMedicalCenter().getName() %>-<%= activity.getSpeciality().getName() %></li>
	<% } %>
	</ul>
	 

    <!-- Optional JavaScript; choose one of the two! -->

    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

    <!-- Option 2: Separate Popper and Bootstrap JS -->
    <!--
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script>
    -->
  </body>
</html>