<jsp:useBean id="controller" scope="page" class="fr.dauphine.mido.doctophine.controller.AppointmentController">
	<jsp:setProperty name="controller" property="request" value="<%=request%>" />
	<jsp:setProperty name="controller" property="response" value="<%=response%>" />
	<jsp:setProperty name="controller" property="*" />
	<jsp:setProperty name="controller" property="opCancel" value="true" />
</jsp:useBean>

<%controller.init();%>