

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
										"<a class='btn btn-info btn-md addMovieToCinema' href='/movies/addMovieToCinema/" + data[i].id + "'>Dodaj</a>  " +
										"<a id='editButton"+data[i].id+"'  class='btn btn-info btn-md editCinema' href='/cinemas/editCinema/" + data[i].id + "'>Izmeni</a></p>" +
										"<div id='quickList_"+data[i].id+"' class='btn btn-info btn-md quickList'>Karte sa popustima</div>" +
										"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
											"<button id='getHalls_" + data[i].id + "' type='button'  data-toggle='modal' data-target='#hallsDialog' class='btn btn-info getHallsBtn'>Sale</button>" +
											"<button id='addHalls_" + data[i].id + "' type='button' data-toggle='modal' data-target='#addHallDialog' class='btn btn-info addHallDialog'>+</button>" +
										"</div>" +
									"</div>";
							
							moviesList = "<div style='display: none' class='movieListDiv container-fluid' id='cinema"+data[i].id+"'>";
							
							moviesList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].movies.length; j++){
								
								movie = "<div class='movieDiv movieClass_" + data[i].movies[j].id + "' id='movieBox_" + data[i].id + "_" + data[i].movies[j].id + "'>" +
											"<h3 class='cinemaName'>" + data[i].movies[j].movieName + "</h3>" +
											"<p><label>Zanr: </label><span>" + data[i].movies[j].genre + "</span></p>" +
											"<p><label>Reditelj: </label><span>" + data[i].movies[j].director + "</span></p>" +
											"<p><label>Trajanje: </label><span>" + data[i].movies[j].runtime + " min</span></p>" +
											"<p><label>Glumci: </label><span>" + data[i].movies[j].actors + "</span></p>" +
											"<p><label>Opis: </label><span>" + data[i].movies[j].description + "</span></p>" +
											"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data[i].movies[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><a class='btn btn-info btn-md deleteMovie' href='/movies/deleteMovie/" + data[i].movies[j].id + "'>Obrisi</a></p>" +
											"<p><a class='btn btn-info btn-md editMovie' href='/movies/editMovie/" + data[i].movies[j].id + "'>Izmeni</a></p>" +
											"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
												"<button id='getProjections_" + data[i].movies[j].id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button>" +
												"<button id='addProjection_" + data[i].id + "' type='button' data-toggle='modal' data-target='#addProjectionDialog' class='btn btn-info addProjectionDialog'>+</button>" +
											"</div>" +
											
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
	
	
	
	$(document).on('click', '.addProjectionBtn', function(){
		
		price = $('#projectionPrice').val()
		date = $('#projectionDate').val()
		time = $('#projectionTime').val()
		movieId = $('#projectionMovie').val()
		hallId = $('#hallSelect option:selected').attr('value').split('_')[1]
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0]
		
		datetime = dateFormat + " " + time
		console.log(datetime)
		
		formData = JSON.stringify({
			price: price,
			date: datetime,
			movieId: movieId,
			hallId: hallId
		});
		
		$.ajax({
			url: "/projections/addProjection/",
			type: "POST",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(data){
				alert("Uspesno dodata projekcija!")
			}
		})
		
		$('#hallSelect').empty();
		$('#projectionHallHolder').empty();
		
	});
	
	$(document).on('click', '.addProjectionDialog', function(){
		
		$('#hallSelect').empty();
		$('#projectionHallHolder').empty();
		
		//smestanje id filma u skriveno polje modalnog dijaloga za dodavanje projekcije
		movieId = $(this).prev().attr('id').split('_')[1]
		$('#projectionMovie').val(movieId)
		
		cinemaId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "halls/" + cinemaId
		}).then(function(data){
			option = "<option>Sala</option>"
		
			for(i = 0; i < data.length; i++){
				
				option += "<option value='hall_" + data[i].id + "'>" + data[i].hallName + "</option>"
				
			}
			$('#hallSelect').append(option);
		})
		
	});
	
	
	$(document).on('click', '.getProjectionsBtn', function(){
		

		movieId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "projections/movie/" + movieId
		}).then(function(data){

			if(data.length === 0){
				$('#movieProjectionsHolder').empty()
				$('#movieProjectionsHolder').append('<p>Nema projekcija :(</p>')
			}else{
				$('#movieProjectionsHolder').empty()
			}
			
			for(var i = 0; i < data.length; i++){
				
				projection = "<div class='container-fluid col-xs-12 hall'>" +
								"<p>Sala: " + data[i].hallHallName + "</p>" +
								"<p>Cena: " + data[i].price + "</p>" +
								"<p>Datum: " + data[i].date + "</p>" +
								"<p>Vreme: " + data[i].time + "</p>" +
							"</div>"
				
				$('#movieProjectionsHolder').append(projection)
			}
			
		});
		
	});
	
	$(document).on('click', '.addHallBtn', function(){
		
		name = $('#hallName').val()
		rows = $('#hallRows').val()
		cols = $('#hallColumns').val()
		cinemaId = $('#hallCinemaId').val()
		
		var seats = []
		
		for(var i = 1; i <= rows; i++){
			for(var j = 1; j <= cols; j++){
				
				var seat = {}
				seat.rowNumber = i
				seat.columnNumber = j
				seat.isReserved = false
				
				seats.push(seat);
				
			}
		}
		
		formData = JSON.stringify({
			hallName: name,
			seats: seats
		});
		
		$.ajax({
			url: "/halls/addHall/" + cinemaId,
			type: "POST",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(data){
				alert("Uspesno dodata sala: " + data.hallName)
			}
		})
		
		
		
	});
	
	$(document).on('click', '.addHallDialog', function(){
		cinemaId = $(this).attr('id').split('_')[1]
		console.log(cinemaId)
		$('#hallCinemaId').val(cinemaId);
	});
	
	$(document).on('click', '.getHallsBtn', function(){
		
		cinemaId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "halls/" + cinemaId
		}).then(function(data){
			
			if(data.length === 0){
				$('#cinemaHallsHolder').empty()
				$('#cinemaHallsHolder').append('<p>Nema sala :(</p>')
			}else{
				$('#cinemaHallsHolder').empty()
			}
			
			
			for(var i = 0; i < data.length; i++){
				
				h = data[i]
				
				hall = "<div id='hallDiv_" + data[i].id + "' class='container-fluid col-xs-12 hall'>" + "<h4>" + data[i].hallName + "</h4>" + generateHallConf(h) + "" +
							"<a href='/halls/deleteHall/" + data[i].id + "' class='btn btn-danger deleteHallBtn'>Obrisi</a>" +
							"<button type='button' data-dismiss='modal' id='/halls/editHall/" + data[i].id + "' class='btn btn-info editHallDialogBtn'>Izmeni</a>" +
						"</div>"
				
				$('#cinemaHallsHolder').append(hall)
			}
			
		});
		
	});
	
	$(document).on('click', '.editHallDialogBtn', function(){
		
		$('#editHallDialog').modal()
		
		url = $(this).attr('id')
		
		$('#editHallId').val(url)
		$('#editHallName').val($(this).prev().prev().prev().text())
		$('#editHallRows').val('')
		$('#editHallColumns').val('')
	})
	
	$(document).on('click', '.editHallBtn', function(){
		
		var hallName = $('#editHallName').val()
		var rows = $('#editHallRows').val()
		var cols = $('#editHallColumns').val()
		var url = $('#editHallId').val()
		
		var seats = []
		
		for(var i = 1; i <= rows; i++){
			for(var j = 1; j <= cols; j++){
				
				var seat = {}
				seat.rowNumber = i
				seat.columnNumber = j
				seat.isReserved = false
				
				seats.push(seat);
			}
		}
		
		formData = JSON.stringify({
			hallName : hallName,
			seats : seats
		})
		
		$.ajax({
			url: url,
			type: "PUT",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(){
				alert("Uspesno editovana sala: " + hallName)
			}
			
		})
		
		
		
	})
	
	$(document).on('click', '.deleteHallBtn', function(event){
		
		event.preventDefault();
		
		id = $(this).attr('href').split('/')[3]
		
		$.ajax({
			url: $(this).attr('href'),
			type: "DELETE",
			success: function(){
				
				hallId = "#hallDiv_" + id
				$(hallId).remove()
				
			}
		})
		
	})
	
	$(document).on('click', '.deleteTicketBtn', function(){
		
		event.preventDefault();
		
		ticketId = $(this).attr('href');
		console.log(ticketId)
		
		$.ajax({
			url: "tickets/" + ticketId,
			type: "DELETE",
			success: function(){
				id = "#ticket_" + ticketId
				$(id).remove()
			}
		})
		
	});
	
	$(document).on("click", ".quickList", function(){
		cinemaId = ($(this).attr('id')).split('_')[1]
		$('#cinemaHallsHolder').empty()
		getQuicks(cinemaId);
	});
	
	$(document).on("click", ".createQuick", function(){
		
		event.preventDefault();
		
		createQuickForm($(this).attr("href"));
		
	});
	
	$(document).on("click", "#addQuickButton", function(){
		
		event.preventDefault();
		
		projectionId = $('#projectionSelect option:selected').attr('value').split('_')[1]
		
		i = document.getElementById("projectionSelect").selectedIndex
		movieNameTemp = document.getElementById("projectionSelect").options[i].text
		movieName = movieNameTemp.split('|')[0].trim();
		
		discount = $('#discountInput').val()
		cinemaId = $('#cinemaTicketId').val()
		
		checkedBoxes = $('.isChecked:checkbox:checked')
		
		//			za svako sediste napraviti po kartu
		
		for(i = 0; i < checkedBoxes.length; i++){
			seatId = checkedBoxes[i].id.split('_')[1]
			
			formData = JSON.stringify({
				projectionId: projectionId,
				projectionMovieName: movieName,
				discount: discount,
				projectionMovieCinemaId: cinemaId,
				seatId: seatId
			});
			
			$.ajax({
				url: "http://localhost:8080/tickets/createQuick",
				type: "POST",
				contentType: "application/json",
				dataType: "json",
				data: formData,
				success: function(data){
					
					$('#addQuickForm').fadeOut()
					alert('Uspesno dodate karte!')
				}
			})
			
		}
		
	})
	
	
	$('#projectionSelect').change(function(){
		
		if(document.getElementById("projectionSelect").selectedIndex === 0){
			$('#hallHolder').empty();
		}else{
			temp = $('#projectionSelect option:selected').attr('value')
			projId = temp.split('_')[1]
			
			getHallConf(projId)
		}
		
	})
	
	$('#hallSelect').change(function(){
		
		if(document.getElementById("hallSelect").selectedIndex === 0){
			$('#projectionHallHolder').empty();
		}else{
			temp = $('#hallSelect option:selected').attr('value')
			hallId = temp.split('_')[1]
			
			getHallProjConf(hallId)
		}
		
	})
	
	$("#goBackMovies").click(function(){
		
		$(".movieListDiv").fadeOut()
		$(".cinemaDiv").delay(500).fadeIn()
		
		$("#goBackMovies").fadeOut()
		$("#goHome").delay(500).fadeIn()
		
	});
	
	$("#goHome").click(function(){
		
		$('#addQuickForm').fadeOut()
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
	
	$(document).on("click",".deleteMovie", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var movieBoxToDelete = $(this).parent().parent().attr("id")
		
		$.ajax({
			url: url,
			type: "DELETE",
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
			actors = $("#actorsAdd").val().split(",")
			
			
			var inputField = document.getElementById("imageAdd");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			console.log(inputField)
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					movieName: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					image: btoa(reader.result),
					actors: actors
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv' id='movieBox_" + str[3] + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.movieName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovie' href='/movies/deleteMovie/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/movies/editMovie/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button>" +
										"<button id='addProjection_" + data.cinemaId + "' type='button' data-toggle='modal' data-target='#addProjectionDialog' class='btn btn-info addProjectionDialog'>+</button>" +
									"</div>" +
								"</div>";
						
						$(movieBoxList).append(movie)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				
				formData = JSON.stringify({
					movieName: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					actors: actors
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv' id='movieBox_" + str[3] + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.movieName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovie' href='/movies/deleteMovie/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/movies/editMovie/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button>" +
										"<button id='addProjection_" + data.cinemaId + "' type='button' data-toggle='modal' data-target='#addProjectionDialog' class='btn btn-info addProjectionDialog'>+</button>" +
									"</div>" +
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
		
		$("#nameEdit").val(name)
		$("#genreEdit").val(genre)
		$("#directorEdit").val(director)
		$("#runtimeEdit").val(runtime.split(" ")[0])
		$("#actorsEdit").val(actors)
		$("#descriptionEdit").val(description)
		
		
		$("#editMovieModal").modal("toggle")
		
		$("#editMovie").click(function(){
			
			name = $("#nameEdit").val();
			genre = $("#genreEdit").val();
			director = $("#directorEdit").val();
			runtime = $("#runtimeEdit").val();
			description = $("#descriptionEdit").val();
			actors = $("#actorsEdit").val().split(",")
			
			var inputField = document.getElementById("imageEdit");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					movieName: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					image: btoa(reader.result),
					actors: actors
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv' id='movieBox_" + data.cinemaId + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.movieName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovie' href='/movies/deleteMovie/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/movies/editMovie/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button>" +
										"<button id='addProjection_" + data.cinemaId + "' type='button' data-toggle='modal' data-target='#addProjectionDialog' class='btn btn-info addProjectionDialog'>+</button>" +
									"</div>" +
								"</div>";
						
						$("."+movieClass).replaceWith(movie)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				formData = JSON.stringify({
					movieName: name,
					genre: genre,
					director: director,
					runtime: runtime,
					description: description,
					actors: actors,
				});
				
				$.ajax({
					url: url,
					type: "POST",
					contentType: "application/json",
					dataType: "json",
					data: formData,
					success: function(data){
						
						movie = "<div class='movieDiv' id='movieBox_" + data.cinemaId + "_" + data.id + "'>" +
									"<h3 class='cinemaName'>" + data.movieName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deleteMovie' href='/movies/deleteMovie/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editMovie' href='/movies/editMovie/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button>" +
										"<button id='addProjection_" + data.cinemaId + "' type='button' data-toggle='modal' data-target='#addProjectionDialog' class='btn btn-info addProjectionDialog'>+</button>" +
									"</div>" +
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
											"<p><label>Trajanje: </label>" + data[i].plays[j].runtime + " min</p>" +
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