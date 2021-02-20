
<%@ include file="/fragments/header.jspf"%>
 
<link rel="stylesheet" href="css/inscription_doctor.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  
<%@ page import="fr.dauphine.mido.doctophine.model.*"%>
<%@ page import="fr.dauphine.mido.doctophine.service.*"%>
<%@ page import="java.util.*"%>
<% 
    Doctor currentDoctor = (Doctor) request.getAttribute("newDoctor");
	Patient admin = (Patient) request.getAttribute("admin");
	ArrayList<MedicalCenter> medicalCenters = (ArrayList<MedicalCenter>) request.getAttribute("medicalCenters");
	ArrayList<Speciality> specialities = (ArrayList<Speciality>) request.getAttribute("specialities");
	
%>
   
	<div style="position: absolute; top: 110px; left: 530px;">
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

	<div class="main-block1">
		<form method="Post" action="DoctorAffectationController">
			<input type="hidden" name="currentDoctor" value=<%= currentDoctor.getEmail() %> />
			<fieldset>
				<legend>
					<h3>Affectation du nouveau medecin</h3>
				</legend>
				<div style="padding-right:15px;padding-left:15px;margin-right:auto;margin-left:auto">
					<table id="myTable" class=" table order-list">
						<thead>
							<tr>
								<td style="padding-left: 10px; font-family: arial">Nom du medecin</td>
								<td style="padding-left: 10px; font-family: arial">Centre médical</td>
								<td style="padding-left: 10px; font-family: arial">Spécialité</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="position:relative;min-height:1px;padding-right:15px;padding-left:15px;width:33.33333333%">
								<select class="form-control" >
										<option selected><%= currentDoctor.getFullName() %></option> 
								</select>
								</td>
								<td style="position:relative;min-height:1px;padding-right:15px;padding-left:15px;width:33.33333333%">
								<select class="form-control" name="medicalcenter"  >
										<option value="" selected>Choisir un centre medical</option>
										<% for(int i=0; i<medicalCenters.size(); i++){ %>
										<option value=<%=medicalCenters.get(i).getId() %> ><%=medicalCenters.get(i).getName() %></option>
										<% } %>
								</select>
								</td>
								<td style="position:relative;min-height:1px;padding-right:15px;padding-left:15px;width:33.33333333%">
								<select class="form-control" name="speciality"  >
										<option value="" selected>Choisir une spécialité</option> 
										<% for(int i=0; i<specialities.size(); i++){ %>
										<option value=<%=specialities.get(i).getId() %> ><%=specialities.get(i).getName() %></option>
										<% } %>
								</select>
								</td>  
							</tr>
						</tbody> 
					</table>
				</div>
			</fieldset>
			<button type="submit" name="addAffectation" id="btn_submit" style="background-color: #0596DE">Ajouter une affectation</button>
			<button type="submit" name="confirme" id="btn_submit" style="background-color: #0596DE">Submit</button> 
		</form>
	</div>
 

<%@ include file="/fragments/footer.jspf"%>