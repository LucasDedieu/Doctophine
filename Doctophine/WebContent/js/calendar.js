$(document).ready(function(){
	init();
});

const init = () => {
	$('[data-toggle="popover"]').popover();  
	$('table').tableCellsSelection();
	$(document).on("click",".enable-btn",enable);
	$(document).on("click",".disable-btn",disable);
	$(document).on("change",".medical-center-chooser",submit);
} 

const enable = (event) => {
	checkSelectedCells();
	submitAction("opEnable");
}


const disable = (event) => {
	checkSelectedCells();
	submitAction("opDisable");
}

const checkSelectedCells = () => {
	let $selectedCells = $('table').tableCellsSelection('selectedCells');
	$selectedCells.each(function(){
		$(this).find("input").prop("checked", true);
	});
}

const submitAction = (actionString) => {
	let $form = $(document).find("form");
	$form.append('<input type="hidden" name="'+actionString+'" value="true"/>');
	$form.submit();
}

const submit = (actionString) => {
	let $form = $(document).find("form");
	$form.submit();
}