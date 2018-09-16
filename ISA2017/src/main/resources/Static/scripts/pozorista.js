$(document).ready(function(event){
	
	console.log("Ucitavanje pozorista");
	
	$(".pozoristePregled").click(function(){
		console.log("poz");
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
							"<p><div id='"+data[i].id+"' class='btn btn-info btn-md playsListButton'>Pogledaj</div></p>" +
							"</div>"
						
							
							playsList = "<div style='display: none' class='playsListDiv container-fluid' id='theatre"+data[i].id+"'>";
							
							playsList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].plays.length; j++){
								
								play = "<div class='movieDiv' id='playBox" + data[i].id + "_" + data[i].plays[j].id + "'>" +
											"<h3 class='cinemaName'>" + data[i].plays[j].name + "</h3>" +
											"<p><label>Zanr: </label>" + data[i].plays[j].genre + "</p>" +
											"<p><label>Reditelj: </label>" + data[i].plays[j].director + "</p>" +
											"<p><label>Trajanje: </label>" + data[i].plays[j].runtime + "</p>" +
											"<p><label>Glumci: </label>" + data[i].plays[j].actors + "</p>" +
											"<p><label>Opis: </label>" + data[i].plays[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].plays[j].projectionTimes + "</p>"+ 
											
											"<p><label>Cena: </label>" + data[i].plays[j].price + "din</p>" +
											"<p><label>Slika: </label><img src='data:image/png;base64, "+data[i].plays[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><div id='"+data[i].id+"' class='btn btn-info btn-md playsListButtonNext'>Pogledaj</div></p>" +
										"</div>";
											/*for(z=0;z< data[i].plays[j].projectionTimes.length;z++){
												play += "<p><label>Vreme projekcije: </label>"+"<option value=\""+data[i].plays[j].id +"\" >"+ data[i].plays[j].projectionTimes[z] +"</option>"+"</p>" +
													//console.log( data[i].plays[j].projectionTimes[z]);
												}*/
										
									playsList = playsList + play;
								}
							
							
							playsList = playsList+"</div>"
							$(".theatreHolder").append(playsList).append(prikaz);
	        	}
	        
	  
	  
	        }});
		
	});
	
	  
	
	$(document).on('click', '.getQuickTicketsTheatreBtn', function(){		// popunjava modalni dijalog kartama sa popustom za odabrani pozoriste
		
		$('#quickTicketsHolder').empty()
		
		cinemaId = $(this).attr('id').split('_')[1]
		
		console.log("dobavljanje karata sa popustom za bioskop: " + cinemaId)
		
		$.ajax({
			url: "tickets/theatre/" + cinemaId
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
									"<h3 class='panel-title text-center'>" + data[i].projectionPlayName + "</h3>" +
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
	
	
	
});
$(document).on("click",".playsListButton", function () {
	
	$("#goBackTheatre").hide();
	$(".cinemaDiv").fadeOut();
	$("#theatre" + $(this).attr("id")).delay(500).fadeIn();
	$("#goBackPlays").delay(500).show();
});
$(document).on("click",".playsListButtonNext", function () {
	
	$("#goBackTheatre").hide();
	$(".cinemaDiv").fadeOut();
	$("#theatre" + $(this).attr("id")).delay(500).fadeOut();
	$("#goBackPlays").delay(500).show();
});


