function getQuicks(cinemaId){
	
	$('.cinemaHolder').empty();
	
	addNew = "<div><a class='btn btn-lg btn-primary createQuick' href='" + cinemaId + "'>Dodaj</a></div>"
	$(".cinemaHolder").append(addNew);
	
	$.ajax({
		url: "/tickets/"+cinemaId
	}).then(function(data){
		
		for(i = 0; i < data.length; i++){
			
			ticket = "<div class='cinemaDiv col-md-6' id=ticket_" + data[i].id + ">" +
				"<h2>" + data[i].movieName + "</p>" +
				"<p>Cena: " + data[i].price + "</p>" +
				"<p>Popust: " + data[i].discount + "%</p>" +
				"<p>Datum projekcije: " + data[i].date + "</p>" +
				"<p>Vreme projekcije: " + data[i].time + "</p>" +
				"<p>Sala: " + data[i].hall + "</p>" +
				"<p>Red: " + data[i].seatRow + "</p>" +
				"<p>Kolona: " + data[i].seatColumn + "</p>" +
			"</div>";
			
			$(".cinemaHolder").append(ticket);
			
		}
		
	});
	
}

function createQuick(cinemaId){
	
	$('.cinemaHolder').empty();
	
	// get projections controller
	// projectionDTO da ima movieName
	// dropdown se biraju projekcije
	// sediste preko konfiguracije
	// discount string
	// cinemaId je argument funkcije i vec je unesen i disabled polje
	
}