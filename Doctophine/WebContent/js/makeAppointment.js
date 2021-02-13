$(document).ready(function(){
	init();
});

const init = () => {

	$(document).on("click",".btn-appointment",makeAppointment);
} 

const makeAppointment = (event) => {
	event.preventDefault();
	let text = prompt("Etes vous sur de prendre ce rendez-vous ? Si oui, entrez un motif :");	
	if(text){
		let href = event.currentTarget.getAttribute('href');
		href +="&description="+encodeURI(text);
		document.location = href;
	}
}
