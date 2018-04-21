$(document).ready(function(){
	
	$(".loggedName").append(sessionStorage.getItem('loggedName'))
	
	if(sessionStorage.loggedId === undefined){
		window.location.href='/';
	}
	
	$("#logoutTCadmin").on('click', function (e) {
	    e.preventDefault();
	    $.ajax({
	    	type:'GET',
	    	contentType: "application/json",
			datatype: 'json',
			url:"http://localhost:8080/user/loggoutUser",
			success: function(){
				 sessionStorage.removeItem('loggedId');
				 sessionStorage.removeItem('loggedName')
				 window.location.href='/';
			 },
				error:function(error){
					console.log("e"+error);
				}
		});
	    
	});
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 		BIOSKOPI
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
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
										"<h3 class='cinemaName' id='cinemaName" + data[i].id + "'>" + data[i].name + "</h3>" +
										"<p><label>Adresa: </label><span id='cinemaAddress" + data[i].id + "'>" + data[i].address + "</span></p>" +
										"<p><label>Opis: </label><span id='cinemaDesc" + data[i].id + "'>" + data[i].description + "</span></p>" +
										"<p><div id='"+data[i].id+"' class='btn btn-info btn-md moviesListButton'>Repertoar</div>  " +
										"<a class='btn btn-info btn-md addMovieToCinema' href='/cinemas/addMovieToCinema/" + data[i].id + "'>Dodaj</a>  " +
										"<a id='editButton"+data[i].id+"'  class='btn btn-info btn-md editCinema' href='/cinemas/editCinema/" + data[i].id + "'>Izmeni</a></p>" +
									"</div>";
							
							moviesList = "<div style='display: none' class='movieListDiv container-fluid' id='cinema"+data[i].id+"'>";
							
							moviesList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].movies.length; j++){
								
								movie = "<div class='movieDiv movieClass_" + data[i].movies[j].id + "' id='movieBox_" + data[i].id + "_" + data[i].movies[j].id + "'>" +
											"<h3 class='cinemaName'>" + data[i].movies[j].name + "</h3>" +
											"<p><label>Zanr: </label><span>" + data[i].movies[j].genre + "</span></p>" +
											"<p><label>Reditelj: </label><span>" + data[i].movies[j].director + "</span></p>" +
											"<p><label>Trajanje: </label><span>" + data[i].movies[j].runtime + "</span></p>" +
											"<p><label>Glumci: </label><span>" + data[i].movies[j].actors + "</span></p>" +
											"<p><label>Opis: </label><span>" + data[i].movies[j].description + "</span></p>" +
											"<p><label>Vreme projekcije: </label><span>" + data[i].movies[j].projectionTimes + "</span></p>" +
											"<p><label>Cena: </label><span>" + data[i].movies[j].price + "din</span></p>" +
											"<p><label>Prosecna ocena: </label><span>" + data[i].movies[j].rating + "</span></p>" +
											"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data[i].movies[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + data[i].id + "/" + data[i].movies[j].id + "'>Obrisi</a></p>" +
											"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data[i].movies[j].id + "'>Izmeni</a></p>" +
											
										"</div>";
								
								
								
								moviesList = moviesList + movie;
							}
							
							moviesList = moviesList+"</div>"
							
							$(".cinemaHolder").append(moviesList).append(cinema);
						}
						

						$("#goHome").fadeIn()
						$(".cinemaHolder").fadeIn()
						
						
					});
		});
		
		
		
	});
	
	$("#goBackMovies").click(function(){
		
		$(".movieListDiv").fadeOut()
		$(".cinemaDiv").delay(500).fadeIn()
		
		$("#goBackMovies").fadeOut()
		$("#goHome").delay(500).fadeIn()
		
	});
	
	$("#goHome").click(function(){
		
		$("#goBackPlays").fadeOut()
		$("#goBackMovies").fadeOut()
		$("#goHome").fadeOut()
		$(".reportHolder").fadeOut();
		$(".cinemaHolder").fadeOut();
		$(".theatreHolder").fadeOut();
		$(".profileHolder").fadeOut();
		$(".buttonsHolder").delay(500).fadeIn();
		
	});

	$(document).on("click",".moviesListButton", function () {
		
		$(".cinemaDiv").fadeOut();
		$("#cinema" + $(this).attr("id")).delay(500).fadeIn();
		$("#goBackMovies").delay(500).fadeIn();
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
			price = $("#priceAdd").val().replace("din", "");
			actors = $("#actorsAdd").val().split(",")
			projectionTimes = $("#projectionTimesAdd").val().split(",")
			rating = $("#ratingAdd").val()
			
			
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
					image: btoa(reader.result),
					actors: actors,
					projectionTimes: projectionTimes,
					rating: rating
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
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + "</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Vreme projekcije: </label><span>" + data.projectionTimes + "</span></p>" +
									"<p><label>Cena: </label><span>" + data.price + "din</span></p>" +
									"<p><label>Prosecna ocena: </label><span>" + data.rating + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + str[3] + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$(movieBoxList).append(movie)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				
				formData = JSON.stringify({
					name: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					price: price,
					actors: actors,
					projectionTimes: projectionTimes,
					rating: rating
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
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + "</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Vreme projekcije: </label><span>" + data.projectionTimes + "</span></p>" +
									"<p><label>Cena: </label><span>" + data.price + "din</span></p>" +
									"<p><label>Prosecna ocena: </label><span>" + data.rating + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + str[3] + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$(movieBoxList).append(movie)
						
					}
				});
			}
			
			$("#addMovieToCinemaModal").modal("toggle")
			
		});
		
	});
	
	$(document).on("click",".editMovie", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var movieClass = $(this).parent().parent().attr("class").split(" ")[1] //  klasa filma koji smo editovali da bi se promenio svaki film na stranici
		var cinemaId = $(this).parent().parent().attr("id").split("_")[1]
		
		/*
		 * 			popunjavanje polja
		 * */
		
		parentId = "#" + $(this).parent().parent().attr("id")
		//$(parentId).children()[0].children[1].innerText
		
		name = $(parentId).children()[0].innerText
		genre = $(parentId).children()[1].children[1].innerText
		director = $(parentId).children()[2].children[1].innerText
		runtime = $(parentId).children()[3].children[1].innerText
		actors = $(parentId).children()[4].children[1].innerText
		description = $(parentId).children()[5].children[1].innerText
		projectionTimes = $(parentId).children()[6].children[1].innerText
		price = $(parentId).children()[7].children[1].innerText
		rating = $(parentId).children()[8].children[1].innerText
		
		$("#nameEdit").val(name)
		$("#genreEdit").val(genre)
		$("#directorEdit").val(director)
		$("#runtimeEdit").val(runtime)
		$("#actorsEdit").val(actors)
		$("#descriptionEdit").val(description)
		$("#projectionTimesEdit").val(projectionTimes)
		$("#priceEdit").val(price)
		$("#ratingEdit").val(rating)
		
		
		$("#editMovieModal").modal("toggle")
		
		$("#editMovie").click(function(){
			
			name = $("#nameEdit").val();
			genre = $("#genreEdit").val();
			director = $("#directorEdit").val();
			runtime = $("#runtimeEdit").val();
			description = $("#descriptionEdit").val();
			price = $("#priceEdit").val().replace("din", "");
			actors = $("#actorsEdit").val().split(",")
			projectionTimes = $("#projectionTimesEdit").val().split(",")
			rating = $("#ratingEdit").val()
			
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
					image: btoa(reader.result),
					actors: actors,
					projectionTimes: projectionTimes,
					rating: rating
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
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + "</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Vreme projekcije: </label><span>" + data.projectionTimes + "</span></p>" +
									"<p><label>Cena: </label><span>" + data.price + "din</span></p>" +
									"<p><label>Prosecna ocena: </label><span>" + data.rating + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + cinemaId + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$("."+movieClass).replaceWith(movie)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				formData = JSON.stringify({
					name: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					price: price,
					actors: actors,
					projectionTimes: projectionTimes,
					rating: rating
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
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + "</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Vreme projekcije: </label><span>" + data.projectionTimes + "</span></p>" +
									"<p><label>Cena: </label><span>" + data.price + "din</span></p>" +
									"<p><label>Prosecna ocena: </label><span>" + data.rating + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovieInCinema' href='/cinemas/deleteMovieInCinema/" + cinemaId + "/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/cinemas/editMovie/" + data.id + "'>Izmeni</a></p>" +
								"</div>";
						
						$("."+movieClass).replaceWith(movie)
						
					}
				});
			}
			
			$("#editMovieModal").modal("toggle")
			
		});
		
	});
	
	$(document).on("click",".editCinema",function(){
		
		event.preventDefault();
		
		url = $(this).attr("href")
		
		nameToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[0] ).attr("id")
		addressToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[1].children[1] ).attr("id")
		descriptionToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[2].children[1] ).attr("id")
		
		$("#cinemaNameEdit").val($(nameToReplace).text())		 //ime u polje
		$("#cinemaAddressEdit").val($(addressToReplace).text())		 //adresa
		$("#cinemaDescriptionEdit").val($(descriptionToReplace).text())		 //opis
		
		$("#editCinemaModal").modal("toggle")
		
		$("#editCinema").click(function(){
			
			name = $("#cinemaNameEdit").val()
			address = $("#cinemaAddressEdit").val()
			description = $("#cinemaDescriptionEdit").val()

			
			formData = JSON.stringify({
				name: name,
				address: address,
				description: description
			});
			
			$.ajax({
				url: url,
				type: "POST",
				contentType: "application/json",
				dataType: "json",
				data: formData,
				success: function(data){
					
					$(nameToReplace).replaceWith("<h3 class='cinemaName' id='cinemaName" + data.id + "'>" + data.name + "</h3>")
					$(addressToReplace).replaceWith("<span id='cinemaAddress" + data.id + "'>" + data.address + "</span>")
					$(descriptionToReplace).replaceWith("<span id='cinemaDesc" + data.id + "'>" + data.description + "</span>")
					
				}
			});
			
			$("#editCinemaModal").modal("toggle")
		});
		
	});
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 					POZORISTA
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
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
											"<p><label>Opis: </label>" + data[i].plays[j].description + "</p>" +
											"<p><label>Vreme projekcije: </label>" + data[i].plays[j].projectionTimes + "</p>" +
											"<p><label>Cena: </label>" + data[i].plays[j].price + "din</p>" +
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