  $(document).ready(function(){
	
	/*
	 * 		Akcije za dugme Bioskopi
	 * */
	
	$("#cinemaButton").click(function(){
		
		$(".buttonsHolder").fadeOut(1000, function(){
			$.ajax({
				url : "http://localhost:8080/cinemas/getCinemas"
			}).then(
					function(data) {
						
						$(".cinemaHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							prikaz = "<div class='cinemaDiv'>" +
									"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
									"<p><label>Adresa: </label>" + data[i].address + "</p>" +
									"<p><label>Opis: </label>" + data[i].description + "</p>" +
									"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
									"</div>"
								
							
							$(".cinemaHolder").append(prikaz)
						}
						

						$("#goBackCinema").fadeIn(1000, function(){
							$(".cinemaHolder").fadeIn(1000)
						})
						
						
					});
		});
		
		
		
	});
	
	$("#goBackCinema").click(function(){
		
		$(".cinemaHolder").fadeOut(1000, function(){
			$(".buttonsHolder").fadeIn(1000)
		})
		
		$("#goBackCinema").fadeOut()
		
	});
	
	/*
	 * 		Akcije za dugme Pozorista
	 * */
	
	$("#theatreButton").click(function(){
		
		$(".buttonsHolder").fadeOut(1000, function(){
			$.ajax({
				url : "http://localhost:8080/theatres/getTheatres"
			}).then(
					function(data) {
						
						$(".theatreHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							prikaz = "<div class='cinemaDiv'>" +
									"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
									"<p><label>Adresa: </label>" + data[i].address + "</p>" +
									"<p><label>Opis: </label>" + data[i].description + "</p>" +
									"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
									"</div>"
								
							
							$(".theatreHolder").append(prikaz)
						}
						

						$("#goBackTheatre").fadeIn(1000, function(){
							$(".theatreHolder").fadeIn(1000)
						})
						
						
					});
		});
		
		
		
	});
	
	$("#goBackTheatre").click(function(){
		
		$(".theatreHolder").fadeOut(1000, function(){
			$(".buttonsHolder").fadeIn(1000)
		})
		
		$("#goBackTheatre").fadeOut()
		
	});
	
});