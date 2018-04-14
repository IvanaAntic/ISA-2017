$(document).ready(function(){
	
	/*
	 * 		Akcije za Bioskope
	 * */
	
	$("#cinemaButton").click(function(){
		
		$(".buttonsHolder").fadeOut(function(){
			$.ajax({
				url : "http://localhost:8080/cinemas/getTCadminCinemas"
			}).then(
					function(data) {
						
						$(".cinemaHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							cinema = "<div class='cinemaDiv'>" +
										"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
										"<p><label>Adresa: </label>" + data[i].address + "</p>" +
										"<p><label>Opis: </label>" + data[i].description + "</p>" +
										"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
										"<p><div id='"+data[i].id+"' class='btn btn-info btn-md moviesListButton'>Repertoar</div></p>" +
									"</div>";
							
							moviesList = "<div style='display: none' class='movieListDiv container-fluid' id='cinema"+data[i].id+"'>";
							
							moviesList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].movies.length; j++){
								
								movie = "<div class='movieDiv'>" +
											"<h3 class='cinemaName'>" + data[i].movies[j].name + "</h3>" +
											"<p><label>Zanr: </label>" + data[i].movies[j].genre + "</p>" +
											"<p><label>Reditelj: </label>" + data[i].movies[j].director + "</p>" +
											"<p><label>Trajanje: </label>" + data[i].movies[j].runtime + "</p>" +
											"<p><label>Glumci: </label>" + data[i].movies[j].actors + "</p>" +
											"<p><label>Ocena: </label>" + data[i].movies[j].rating + "</p>" +
											"<p><label>Opis: </label>" + data[i].movies[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].movies[j].projectionTimes + "</p>" +
											"<p><label>Cena: </label>" + data[i].movies[j].price + "</p>" +
										"</div>";
								
								moviesList = moviesList + movie;
							}
							
							moviesList = moviesList+"</div>"
							
							$(".cinemaHolder").append(moviesList).append(cinema);
						}
						

						$("#goBackCinema").fadeIn()
						$(".cinemaHolder").fadeIn()
						
						
					});
		});
		
		
		
	});
	
	$("#goBackCinema").click(function(){
		
		$(".cinemaHolder").fadeOut(function(){
			$(".buttonsHolder").fadeIn()
		})
		
		$("#goBackCinema").fadeOut()
		
	});
	
	$("#goBackMovies").click(function(){
		
		$(".movieListDiv").fadeOut()
		$(".cinemaDiv").delay(500).fadeIn()
		
		$("#goBackMovies").hide()
		$("#goBackCinema").delay(500).show()
		
	});
	
	$(document).on("click",".moviesListButton", function () {
		
		$("#goBackCinema").hide();
		$(".cinemaDiv").fadeOut();
		$("#cinema" + $(this).attr("id")).delay(500).fadeIn();
		$("#goBackMovies").delay(500).show();
	});
	
	
	/*
	 * 		Akcije za Pozorista
	 * */
	
	$("#theatreButton").click(function(){
		
		$(".buttonsHolder").fadeOut(function(){
			$.ajax({
				url : "http://localhost:8080/theatres/getTCadminTheatres"
			}).then(
					function(data) {
						
						$(".theatreHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							prikaz = "<div class='cinemaDiv'>" +
									"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
									"<p><label>Adresa: </label>" + data[i].address + "</p>" +
									"<p><label>Opis: </label>" + data[i].description + "</p>" +
									"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
									"<p><div id='"+data[i].id+"' class='btn btn-info btn-md playsListButton'>Repertoar</div></p>" +
									"</div>"
								
							playsList = "<div style='display: none' class='playsListDiv container-fluid' id='theatre"+data[i].id+"'>";
							
							playsList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].plays.length; j++){
								
								play = "<div class='movieDiv'>" +
											"<h3 class='cinemaName'>" + data[i].plays[j].name + "</h3>" +
											"<p><label>Zanr: </label>" + data[i].plays[j].genre + "</p>" +
											"<p><label>Reditelj: </label>" + data[i].plays[j].director + "</p>" +
											"<p><label>Trajanje: </label>" + data[i].plays[j].runtime + "</p>" +
											"<p><label>Glumci: </label>" + data[i].plays[j].actors + "</p>" +
											"<p><label>Ocena: </label>" + data[i].plays[j].rating + "</p>" +
											"<p><label>Opis: </label>" + data[i].plays[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].plays[j].projectionTimes + "</p>" +
											"<p><label>Cena: </label>" + data[i].plays[j].price + "</p>" +
										"</div>";
								
								playsList = playsList + play;
							}
							
							playsList = playsList+"</div>"
							
							$(".theatreHolder").append(playsList).append(prikaz);
						}
						

						$("#goBackTheatre").fadeIn()
						$(".theatreHolder").fadeIn()
						
						
						
					});
		});
		
		
		
	});
	
	$("#goBackTheatre").click(function(){
		
		$(".theatreHolder").fadeOut(function(){
			$(".buttonsHolder").fadeIn()
		})
		
		$("#goBackTheatre").fadeOut()
		
	});
	
	$("#goBackPlays").click(function(){
		
		$(".playsListDiv").fadeOut()
		$(".cinemaDiv").delay(500).fadeIn()
		
		$("#goBackPlays").hide()
		$("#goBackTheatre").delay(500).show()
		
	});
	
	$(document).on("click",".playsListButton", function () {
		
		$("#goBackTheatre").hide();
		$(".cinemaDiv").fadeOut();
		$("#theatre" + $(this).attr("id")).delay(500).fadeIn();
		$("#goBackPlays").delay(500).show();
	});
	
});