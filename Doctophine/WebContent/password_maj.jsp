<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<%@page import="fr.dauphine.mido.doctophine.model.Patient"%>
<%@ include file="/fragments/header.jspf"%>

	<nav
		style="background: #053569; width: 100%; height: 50px; position: absolute; top: 0px;">

		<div
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 15px; left: 10px">
			<h2>Doctophine</h2>
		</div>

		<%
			Patient p = (Patient) request.getAttribute("patient");
			Doctor doctor = (Doctor) session.getAttribute("doctor");

			if (request.getAttribute("doctor") != null) {
		%>
		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Medecin</center>

		<%
			} else if (request.getAttribute("patient") != null) {
		%>

		<%
			if (!p.isAdmin()) {
		%>

		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Patient</center>

		<%
			} else {
		%>

		<center
			style="color: white; width: 1440px; top: 8px; position: fixed; font-size: 25px; font-family: 'Josefin Sans', sans-serif">
			ESPACE Administrateur</center>

		<%
			}
			}
			;
		%>
	 
	</nav>
	
	<form action="Deconnexion" method="GET">
		<button style="position: absolute; left: 1400px; top: 7px;"
			type="submit" class="btn btn-danger">Deconnexion</button>
	</form>
	
	<div class="form-signin"
		style="position: absolute; top: 90px; left: 460px;">
		<%
                if (request.getAttribute("error_password") != null) {

            %>
		<div class="alert alert-danger" role="alert">
			<%= request.getAttribute("error_password")%>
		</div>
		<% };%>

	</div>
	
	<FORM method="POST" name="myForm" ACTION="ModificationMotDePasseController" onsubmit="return validateForm()" style="position:absolute;top:150px; left:500px; ">
  
            <div style="position:absolute;top:120px;left:100px;"  >
                <label for="inputEmail" class="sr-only">Votre ancien mot de passe</label>
                <INPUT Type=password style="position:absolute;width: 250px;" id="ex_password" name=ex_password class="form-control" placeholder="Votre ancien mot de passe" required autofocus>   
            </div>

            <div  style="position:absolute;top:170px;left:100px;">
                <label for="inputPassword" class="sr-only">Votre nouveau mot de passe</label>
                <INPUT Type=password style="position:absolute;width: 250px;" name=new_password1  id="new_password1" class="form-control" placeholder="Votre nouveau mot de passe" required>
            </div>
            
            <div  style="position:absolute;top:220px;left:100px;">
                <label for="inputPassword" class="sr-only">Retaper votre nouveau mot de passe</label>
                <INPUT Type=password style="position:absolute;width: 250px;" name=new_password2  id="new_password2" class="form-control" placeholder="Retaper votre nouveau mot de passe" required>
            </div>

            <button style=" background-color:#053569;position:absolute;top:270px;left:130px;width: 180px;" type="submit" name="submit" class="btn btn-primary">Modifier </button>
			 
        </FORM>
     <script>
        
        function validateForm() {
    	  var ex_password = document.forms["myForm"]["ex_password"].value;
    	  var new_password1 = document.forms["myForm"]["new_password1"].value;
    	  var new_password2 = document.forms["myForm"]["new_password2"].value;
    	  
  	  	  if (new_password1!=new_password2) {
    	    alert("Veuillez retaper votre mot de passe correctemet.");
    	    return false;
  	  	  }else if (new_password1.length<8){
  	  		alert("Le mot de passe doit contenir au moins 8 caractères alphanumérique.");
    	    return false; 
  	  	  }    	   
    	  
       	  
    	}

    </script>
    
<%@ include file="/fragments/footer.jspf"%>