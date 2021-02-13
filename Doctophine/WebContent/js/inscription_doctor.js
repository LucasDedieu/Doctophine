$(document).ready(function () {
	    var counter = 0;

	    $("#addrow").on("click", function () {
	        var newRow = $("<tr>");
	        var cols = "";
	         
	        cols += '<td class="col-sm-4">'+
					'<select class="form-control"  id="cars" >'+ 
					'<option selected></option>'+
					'</select>'+
					'</td>';
	        
	        cols += '<td class="col-sm-4">'+
					'<select class="form-control"  id="medicalcenter" name="medicalcenter'+counter+'">'+
					'<option selected>Choisir un centre medical</option>'+
					'<option value="Dauphine" >Dauphine</option>'+
					'<option value="Paul Sabatier" >Paul Sabatier</option>'+
					'</select>'+
					'</td>';
	        cols += '<td class="col-sm-3">'+
					'<select class="form-control"  id="speciality" name="speciality'+counter+'">'+
					'<option selected>Choisir une specialite</option>'+
					'<option value="Ophtamologie">Ophtamologie</option>'+
					'<option value="ORL">ORL</option>'+ 
					'</select>'+
					'</td>';
	        

	        cols += '<td><input type="button" class="ibtnDel btn btn-md btn-danger "  value="X"></td>';
	        newRow.append(cols);
	        $("table.order-list").append(newRow);
	        counter++;
	    });



	    $("table.order-list").on("click", ".ibtnDel", function (event) {
	        $(this).closest("tr").remove();       
	        counter -= 1
	    });


	});



	function calculateRow(row) {
	    var price = +row.find('input[name^="price"]').val();

	}

	function calculateGrandTotal() {
	    var grandTotal = 0;
	    $("table.order-list").find('input[name^="price"]').each(function () {
	        grandTotal += +$(this).val();
	    });
	    $("#grandtotal").text(grandTotal.toFixed(2));
	}