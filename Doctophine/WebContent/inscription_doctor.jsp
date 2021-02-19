
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="fr.dauphine.mido.doctophine.model.*"%>
<%@ page import="fr.dauphine.mido.doctophine.service.*"%>
<%@ page import="java.util.*"%>
<%
	Patient admin = (Patient) request.getAttribute("admin");
%>
<!DOCTYPE html>
<html>
<head>
<title>Formulaire d'inscription</title>
<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
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

	<div style="position: absolute; top: 70px; left: 700px;">
		<%
                if (request.getAttribute("error") != null) {

            %>
		<div
			style="position: relative; padding: .75rem 1.25rem; margin-bottom: 1rem; border: 1px solid transparent; border-radius: .25rem; color: #721c24; background-color: #f8d7da; border-color: #f5c6cb; width: 150px;"
			role="alert">
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
	
	<div  class="main-block1">
    <form method="Post" action="InscriptionDoctorController" name="myForm" onsubmit="return validateForm()"> 
    	
      <fieldset>
        <legend>
          <h3>Détails du compte</h3> 
        </legend>
        <div  class="account-details">
          <div><label>Email*</label><input type="text" name="email" required></div>
          <div><label>Password*</label><input type="password" name="password" required></div> 
        </div>
      </fieldset>
      <fieldset> 
        <legend> 
          <h3>Informations personnelles</h3>
        </legend>
        
        <div  class="personal-details"> 
          <div>
            <div><label>Nom*</label><input type="text"  class="vcheck" name="nom" id="nom" required></div>
            <div><label>Prénom*</label><input type="text" class="vcheck" name="prenom" id="prenom" required></div>
            <div><label>Date de naissance</label><input type="date" class="vcheck" name="date" id="date" min="1900-01-01" max="2021-02-14" required></div> 
            <div><label>Téléphone*</label><input type="text" id="tel" class="vcheck" name="tel" id="tel" required></div> 
            <div><label>Rue</label><input type="text" name="rue" id="rue" class="vcheck" ></div>
            <div><label>Code Postal</label><input type="text" class="vcheck" name="code_postal" id="code_postal" required></div>
            <div><label>Ville</label><input type="text" class="vcheck" name="ville" id="ville" required></div>  
          </div>          
        </div>
        
      </fieldset>
      <fieldset></fieldset>     
      
      <button type="submit" id="btn_submit">Suivant</button>
    </form>
    </div> 
    
    <form action="Deconnexion" method="GET">
		<button
			style="position: absolute; left: 1380px; top: 0px; width: 120px"
			type="submit" class="btn btn-danger">Deconnexion</button>
	</form>
    
    <script>
    
    function validateForm() {
    	  var nom = document.forms["myForm"]["nom"].value;
    	  var prenom = document.forms["myForm"]["prenom"].value;
    	  var tel = document.forms["myForm"]["tel"].value;
    	  var rue = document.forms["myForm"]["rue"].value;
    	  var code_postal = document.forms["myForm"]["code_postal"].value;
    	  var ville = document.forms["myForm"]["ville"].value; 
    	  var email = document.forms["myForm"]["email"].value;
    	  var password = document.forms["myForm"]["password"].value;
    	  
    	  if (!email.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
      	    alert("E-mail invalide.");
      	    return false;
        	}
  	  	  if (password.length<8) {
    	    alert("Le mot de passe doit contenir au moins 8 caractères alphanumérique.");
    	    return false;
   	  		}
    	  
    	  if (!nom.match(/^(([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+)?)+([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+[])?$/)) {
    	    alert("Nom invalide.");
    	    return false;
    	  }
    	  if (!prenom.match(/^(([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+)?)+([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+[])?$/)) {
      	    alert("Prénom invalide.");
      	    return false;
      	  }
    	  if (!tel.match(/^(33|0)(1|6|7|9)\d{8}$/)) {
        	    alert("Numéro de téléphone invalide.");
        	    return false;
          } 
    	  if (!code_postal.match(/^([0-9]+)$/) || code_postal.length < 5) {
        	    alert("Code postal invalide.");
        	    return false;
            }   
    	  if (!ville.match(/^(([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+)?)+([A-Za-zéçàè]+[ \-']?)*([A-Za-zéçàè]+[])?$/)) {
        	    alert("Nom de ville invalide."); 
        	    return false; 
          }
    	  
       	  
    	}

    </script>

	 
</body>
</html>