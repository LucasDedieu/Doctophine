<%@ include file="/fragments/header.jspf"%>

<link href="css/login.css" rel="stylesheet">

<div class="login">
	<div class="logo">
		<img src="images/logo.png"/>
	</div>
	<FORM method="POST" ACTION="Login">
		<div class="card">

			<div class="card-header">Authentifiez-vous</div>
			<ul class="list-group list-group-flush">
				<li class="list-group-item">
					<div>
						<label for="inputEmail" class="sr-only">Login</label> <INPUT
							Type=text id="input" name=login class="form-control"
							placeholder="Login" required autofocus>
							
							
									
											
					</div>
				</li>
				<li class="list-group-item">
						<label for="inputPassword" class="sr-only">Password</label> <INPUT
							Type=password name=password id="inputPassword"
							class="form-control" placeholder="Password" required
							>
				</li>
				<li class="list-group-item  button"><button type="submit" name="submit"
						class="btn btn-primary">Connexion</button></li>
						
				<li class="list-group-item">
					<p>
						Vous n'avez pas un compte? <a href="inscription_client.jsp">
							Inscrivez vous. </a>
					</p>
					
				</li>
			</ul>




			<div class="form-signin">
				<% if (request.getSession().getAttribute("valide") != null) {%>
				<div class="alert alert-success" role="alert">
					<%=request.getSession().getAttribute("valide")%>
				</div>
				<%}%>

			</div>
			


			<div class="form-signin" >
				<%
					if (request.getSession().getAttribute("error_login") != null) {
				%>
				<div class="alert alert-danger" role="alert">
					<%=request.getSession().getAttribute("error_login")%>
				</div>
				<%};%>

			</div>
		</div>
	</FORM>
</div>



<%@ include file="/fragments/footer.jspf"%>