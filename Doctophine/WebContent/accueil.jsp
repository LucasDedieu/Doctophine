<%@page import="fr.dauphine.mido.doctophine.model.Doctor"%>
<%@page import="fr.dauphine.mido.doctophine.model.Patient"%>
<%@ include file="/fragments/header.jspf"%>



<link href="css/accueil.css" rel="stylesheet">

<div class="menu">
	<div class="logo">
		<img src="images/logo.png" />
	</div>
	<div class="card">

		<div class="card-header">Menu administrateur</div>
		<ul class="list-group ">
			<li class="list-group-item">
				<a class="dropdown-item" href="FormulaireInscriptionDoctorController">Inscription d'un medecin</a>	       
			</li>
			<li class="list-group-item">
				<a class="dropdown-item disabled" href="#" onclick="return false">Gérer les spécialités</a>
			</li>
			<li class="list-group-item">
				<a class="dropdown-item disabled" href="#" onclick="return false">Modifier un médecin existant</a>
			</li>
			<li class="list-group-item  button">
				<a class="dropdown-item" href="Deconnexion" >Se Deconnecter</a>
			</li>

		</ul>
	</div>

</div>



<%@ include file="/fragments/footer.jspf"%>