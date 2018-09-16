$(document).ready(function(event){
	
	console.log("Ucitavanje bioskopa");
	
/*	$(".bioskopPregled").click(function(){
	  $.ajax({
	        type: 'GET',
	        url: "http://localhost:8080/cinemas/getCinemas",
	        contentType: 'application/json',
	        success: function (data) {


				for(i = 0; i < data.length; i++){
					
					prikaz = "<div class='cinemaDiv'>" +
							"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
							"<p><label>Adresa: </label>" + data[i].address + "</p>" +
							"<p><label>Opis: </label>" + data[i].description + "</p>" +
							"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
							"<p><div id='"+data[i].id+"' class='btn btn-info btn-md moviesListButton'>Repertoar</div>  " +
							"<p><button id='getQuickTickets_" + data[i].id + "' type='button'  data-toggle='modal' data-target='#quickTicketsDialog' class='btn btn-info getQuickTicketsBtn'>Karte sa popustom</button></p>" +
							"</div>"
						
					$(".cinemaHolder").append(prikaz)
				}
	        	}
	  });
	});*/
	
	
	$(document).on('click', '.getQuickTicketsBtn', function(){		// popunjava modalni dijalog kartama sa popustom za odabrani bioskop
		
		$('#quickTicketsHolder').empty()
		
		cinemaId = $(this).attr('id').split('_')[1]
		
		console.log("dobavljanje karata sa popustom za bioskop: " + cinemaId)
		
		$.ajax({
			url: "tickets/cinema/" + cinemaId
		}).then(function(data){
			
			console.log("Karte dobavljene!")
			console.log(data)
			
			if(data.length === 0){
				$('#quickTicketsHolder').append('<p>Nema popusta :(</p>')
				console.log("broj karata je 0")
			}
				
				
			for(var i = 0; i < data.length; i++){
				
				var karta = "<div class='panel panel-default'>" +
								"<div class='panel-heading text-center'>" +
									"<h3 class='panel-title text-center'>" + data[i].projectionMovieName + "</h3>" +
								"</div>" +
								"<div class='panel-body'>" +
									"<p>Popust: " + data[i].discount + "%" + "</p>" +
									"<p>Cena: " + data[i].projectionPrice + "</p>" +
									"<p>Datum: " + data[i].date + "</p>" +
									"<p>Vreme: " + data[i].time + "</p>" +
									"<p>Sala: " + data[i].projectionHallName + "</p>" +
									"<p>Red: " + data[i].seatRowNumber + "</p>" +
									"<p>Sediste: " + data[i].seatColumnNumber + "</p>" +
									"<p><a href='#' class='btn btn-info reserveQuickBtn' id='reserveQuick_" + data[i].id + "'>Rezervisi</a></p>"
								"</div>" +
							"</div>"
								
				$('#quickTicketsHolder').append(karta)
				
			}
			
		})
		
	}) // kraj getQuickTicketsBtn
	
	$(document).on('click', '.reserveQuickBtn', function(event){		// brza rezervacija
		
		event.preventDefault();
		thisId = $(this).attr('id')
		ticketId = thisId.split('_')[1]
		
		$.ajax({
			url: '/tickets/reserveQuick/' + ticketId,
			type: 'PUT',
			success: function(data){
				console.log("Rezervisana karta: " + data.id)
				$("#" + thisId).text('Rezervisano')
				$("#" + thisId).removeClass('btn-info').addClass('btn-success')
				loadVisitHistory();
			},
			error: function(error){
				alert("Karta nije rezervisana!")
			}
		})
		
		
		
	})
   
	  
});