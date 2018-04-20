$(document).ready(function(event){
	
	console.log("Ucitavanje pozorista");
	
	
	  $.ajax({
	        type: 'GET',
	        url: "/theatres/getTheatres",
	        contentType: 'application/json',
	        success: function (data) {

	        	for(i = 0; i < data.length; i++){
	        		

					prikaz = "<div class='cinemaDiv'>" +
							"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
							"<p><label>Adresa: </label>" + data[i].address + "</p>" +
							"<p><label>Opis: </label>" + data[i].description + "</p>" +
							"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
							"<button type='button'>Pogledaj</button>"
							"</div>"
						
					
					$(".theatreHolder").append(prikaz)
	        	}
	        }
	  
	  
	    });
	
	
});

