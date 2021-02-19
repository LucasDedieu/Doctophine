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
		
		<div class="form-signin"  style="position:absolute;top:70px; left:430px; " >
             <%
                if (request.getAttribute("error_suppression") != null) {

            %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error_suppression")%>
            </div>
            <% };%>
           
        </div>
	</nav>

	<table class="table"
		style="margin-left: 300px; margin-top: 150px; width: 50%">
		<tbody>
			<form method="Post" action="ModificationComptePatientController" 
					name="formNom" onsubmit="return validateFormNom()">
				<tr>
					<th>Nom</th>
					<% if (p!=null){ %>
					<td><%=p.getLastName()%></td>
					<% } else{ %>
					<td><%=doctor.getLastName()%></td>
					<%} %>
					<td><input type="text" name="nom" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" 
					name="formPrenom" onsubmit="return validateFormPrenom()">
				<tr>
					<th>Prenom</th>
					<% if (p!=null){ %>	
					<td><%=p.getFirstName()%></td>
					<% }else{ %>
					<td><%=doctor.getFirstName()%></td>
					<%} %>
					<td><input type="text" name="prenom" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" 
					name="formEmail" onsubmit="return validateFormEmail()">
				<tr>
					<th>Email</th>
					<% if (p!=null){ %>
					<td><%=p.getEmail()%></td>
					<% } else{ %>
					<td><%=doctor.getEmail()%></td>
					<%} %>
					<td><input type="text" name="email" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" 
					name="formTel" onsubmit="return validateFormTel()">
				<tr>
					<th>Telephone</th>
					<% if (p!=null){ %>
					<td><%=p.getPhone()%></td>
					<% } else{ %>
					<td><%=doctor.getPhone()%></td>
					<%} %>
					<td><input type="text" name="tel" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" name="formAdresse">
				<tr>
					<th>Adresse</th>
					<% if (p!=null){ %>
					<td><%=p.getAddress()%></td>
					<%}else{ %>
					<td><%=doctor.getAddress()%></td>
					<%} %>
					<td><input type="text" name="adresse" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" >
				<tr>
					<th>Date de naissance</th>
					<% if (p!=null){ %>
					<td><%=p.getBirthDate()%></td>
					<% } else { %>
					<td><%=doctor.getBirthDate()%></td>
					<%} %>
					<td><input type="date" name="date" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
			<form method="Post" action="ModificationComptePatientController" name="formAdresse">
				<tr>
					<th>Photo de profil</th>
					<% if (p!=null){ %>
					<td><%=p.getPhoto()%></td>
					<%}else{ %>
					<td><%=doctor.getPhoto()%></td>
					<%} %>
					<td><input type="text" name="photo" class="form-control"
						required></td>
					<td>
						<button type="submit" class="btn btn-primary">Modifier</button>
					</td>
				</tr>
			</form>
		</tbody>
	</table>


	<form action="Deconnexion" method="GET">
		<button
			style="position: absolute; left: 1380px; top: 7px; width: 120px"
			type="submit" class="btn btn-danger">Deconnexion</button>
	</form>


	<form action="SuppressionCompte" method="GET" style="text-align:center;" >
		<a class="btn btn-secondary" href="password_maj.jsp">Modifier le mot de passe</a>
		<button
			type="submit" class="btn btn-danger">Supprimer mon compte</button>
	</form>

	<script>
    
    function validateFormNom() {
    	  var nom = document.forms["formNom"]["nom"].value; 
    	    
    	  if (!nom.match(/^(([A-Za-zיחאט]+[ \-']?)*([A-Za-zיחאט]+)?)+([A-Za-zיחאט]+[ \-']?)*([A-Za-zיחאט]+[])?$/)) {
    	    
    		  alert("Nom invalide.");
    	      return false;
    	  }   
    }
    
    function validateFormPrenom() {
  	  var prenom = document.forms["formPrenom"]["prenom"].value; 
  	    
  	  if (!prenom.match(/^(([A-Za-zיחאט]+[ \-']?)*([A-Za-zיחאט]+)?)+([A-Za-zיחאט]+[ \-']?)*([A-Za-zיחאט]+[])?$/)) {
  	    
  		  alert("Prenom invalide.");
  	      return false;
  	  }   
  	}
    
    function validateFormEmail() {
    	  var email = document.forms["formPrenom"]["email"].value; 
    	    
    	  if (!email.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)) {
    	    
    		  alert("Email invalide.");
    	      return false;
    	  }   
    	}
    
    function validateFormTel() {
  	  var tel = document.forms["formPrenom"]["tel"].value; 
  	    
  	  if (!tel.match(/^(33|0)(1|6|7|9)\d{8}$/)) {
  	    
  		  alert("Telephone invalide.");
  	      return false;
  	  }   
  	}

    </script>
<%@ include file="/fragments/footer.jspf"%>