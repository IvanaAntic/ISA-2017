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
										"<p><a class='btn btn-info btn-md addMovieToCinema' href='/cinemas/addMovieToCinema/" + data[i].id + "'>Dodaj</a></p>" +
									"</div>";
							
							moviesList = "<div style='display: none' class='movieListDiv container-fluid' id='cinema"+data[i].id+"'>";
							
							moviesList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].movies.length; j++){
								
								movie = "<div class='movieDiv movieClass_" + data[i].movies[j].id + "' id='movieBox_" + data[i].id + "_" + data[i].movies[j].id + "'>" +
											"<h3 class='cinemaName'>" + data[i].movies[j].name + "</h3>" +
											"<p><label>Zanr: </label>" + data[i].movies[j].genre + "</p>" +
											"<p><label>Reditelj: </label>" + data[i].movies[j].director + "</p>" +
											"<p><label>Trajanje: </label>" + data[i].movies[j].runtime + "</p>" +
											"<p><label>Glumci: </label>" + data[i].movies[j].actors + "</p>" +
											"<p><label>Ocena: </label>" + data[i].movies[j].rating + "</p>" +
											"<p><label>Opis: </label>" + data[i].movies[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].movies[j].projectionTimes + "</p>" +
											"<p><label>Cena: </label>" + data[i].movies[j].price + "</p>" +
											"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data[i].movies[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + data[i].id + "/" + data[i].movies[j].id + "'>Obrisi</a></p>" +
											"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data[i].movies[j].id + "'>Izmeni</a></p>" +
											
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
	
	$(document).on("click",".deleteMovieInCinema", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var movieBoxToDelete = $(this).parent().parent().attr("id")
		
		$.ajax({
			url: url,
			type: "POST",
			data: JSON.stringify({}),
			contentType: "application/json",
			success: function(){
				$("#" + movieBoxToDelete).remove();
			}
		});
		
	});
	
	$(document).on("click",".addMovieToCinema", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		str = url.split("/")
		var movieBoxList = "#cinema" + str[3]
		
		$("#addMovieToCinemaModal").modal("toggle")
		
		$("#addMovieToCinema").click(function(){
			
			name = $("#nameAdd").val();
			genre = $("#genreAdd").val();
			director = $("#directorAdd").val();
			runtime = $("#runtimeAdd").val();
			description = $("#descriptionAdd").val();
			price = $("#priceAdd").val();
			
			var inputField = document.getElementById("imageAdd");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					name: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					price: price,
					image: btoa(reader.result)
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv' id='movieBox_" + str[3] + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.name + "</h3>" +
									"<p><label>Zanr: </label>" + data.genre + "</p>" +
									"<p><label>Reditelj: </label>" + data.director + "</p>" +
									"<p><label>Trajanje: </label>" + data.runtime + "</p>" +
									"<p><label>Glumci: </label>" + data.actors + "</p>" +
									"<p><label>Ocena: </label>" + data.rating + "</p>" +
									"<p><label>Opis: </label>" + data.description + "</p>" +
									"<p><label>Vreme projekcije: </label>" + data.projectionTimes + "</p>" +
									"<p><label>Cena: </label>" + data.price + "</p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + str[3] + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$(movieBoxList).append(movie)
						
					}
				});
			  }
			reader.readAsDataURL(imageFile);
			
			$("#addMovieToCinemaModal").modal("toggle")
			
		});
		
	});
	
	$(document).on("click",".editMovie", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var movieClass = $(this).parent().parent().attr("class").split(" ")[1] //  klasa filma koji smo editovali da bi se promenio svaki film na stranici
		var cinemaId = $(this).parent().parent().attr("id").split("_")[1]
		
		
		$("#editMovieModal").modal("toggle")
		
		$("#editMovie").click(function(){
			
			name = $("#nameEdit").val();
			genre = $("#genreEdit").val();
			director = $("#directorEdit").val();
			runtime = $("#runtimeEdit").val();
			description = $("#descriptionEdit").val();
			price = $("#priceEdit").val();
			
			var inputField = document.getElementById("imageEdit");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					name: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					price: price,
					image: btoa(reader.result)
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv movieClass_" + data.id + "' id='movieBox_" + cinemaId + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.name + "</h3>" +
									"<p><label>Zanr: </label>" + data.genre + "</p>" +
									"<p><label>Reditelj: </label>" + data.director + "</p>" +
									"<p><label>Trajanje: </label>" + data.runtime + "</p>" +
									"<p><label>Glumci: </label>" + data.actors + "</p>" +
									"<p><label>Ocena: </label>" + data.rating + "</p>" +
									"<p><label>Opis: </label>" + data.description + "</p>" +
									"<p><label>Vreme projekcije: </label>" + data.projectionTimes + "</p>" +
									"<p><label>Cena: </label>" + data.price + "</p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + cinemaId + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$("."+movieClass).replaceWith(movie)
						
					}
				});
			  }
			reader.readAsDataURL(imageFile);
			
			$("#editMovieModal").modal("toggle")
			
		});
		
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
								
								play = "<div class='movieDiv' id='playBox" + data[i].id + "_" + data[i].plays[j].id + "'>" +
											"<h3 class='cinemaName'>" + data[i].plays[j].name + "</h3>" +
											"<p><label>Zanr: </label>" + data[i].plays[j].genre + "</p>" +
											"<p><label>Reditelj: </label>" + data[i].plays[j].director + "</p>" +
											"<p><label>Trajanje: </label>" + data[i].plays[j].runtime + "</p>" +
											"<p><label>Glumci: </label>" + data[i].plays[j].actors + "</p>" +
											"<p><label>Ocena: </label>" + data[i].plays[j].rating + "</p>" +
											"<p><label>Opis: </label>" + data[i].plays[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].plays[j].projectionTimes + "</p>" +
											"<p><label>Cena: </label>" + data[i].plays[j].price + "</p>" +
											"<p><label>Slika: </label><img src='data:image/png;base64, "+data[i].plays[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><a class='btn btn-info btn-md deletePlayInTheatre' href='/theatres/deletePlayInTheatre/" + data[i].id + "/" + data[i].plays[j].id + "'>Obrisi</a></p>" +
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
	
	$(document).on("click",".deletePlayInTheatre", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var playBoxToDelete = $(this).parent().parent().attr("id")
		
		$.ajax({
			url: url,
			type: "POST",
			data: JSON.stringify({}),
			contentType: "application/json",
			success: function(){
				$("#" + playBoxToDelete).remove();
			}
		});
		
	});
	
});