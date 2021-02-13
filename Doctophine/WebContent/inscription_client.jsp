<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulaire d'inscription</title>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600' rel='stylesheet' type='text/css'>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="css/inscription_doctor.css">
     
  </head>
  <body>
  <nav style="background:#053569 ;width:100%;height: 50px;position:absolute;top:0px;">   </nav>
  	<div style="color:white;width:1440px;top:0px;position:fixed;font-size:15px;  left: 10px">
            <h2>Doctophine</h2>
    </div>
    <div style="color:white;width:1440px;top:0px;position:fixed;font-size:15px;  left: 700px">
            <h2>Inscription Patient</h2>
    </div> 
    
    <div class="form-signin"  style="position:absolute;top:90px; left:460px; " >
             <%
                if (request.getAttribute("error") != null) {

            %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error")%>
            </div>
            <% };%>
           
        </div>
    
    <div  class="main-block1">
    <form method="Post" action="ServletPrincipal" name="myForm" onsubmit="return validateForm()"> 
    	
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