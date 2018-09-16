

$(document).ready(function(){
	
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
	
	$("#theatreButton").click(function(){
		
		$(".buttonsHolder").fadeOut(function(){
			$.ajax({
				url : "http://localhost:8080/theatres/getTCadminTheatres"
			}).then(
					function(data) {
						
						$(".cinemaHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							theatre = "<div class='theatreDiv'>" +
										"<h3 class='theatreName' id='theatreName" + data[i].id + "'>" + data[i].name + "</h3>" +
										"<p><label>Adresa: </label><span id='theatreAddress" + data[i].id + "'>" + data[i].address + "</span></p>" +
										"<p><label>Opis: </label><span id='theatreDesc" + data[i].id + "'>" + data[i].description + "</span></p>" +
										"<p><div id='"+data[i].id+"' class='btn btn-info btn-md playsListButton'>Repertoar</div>  " +
										"<a class='btn btn-info btn-md addPlayToTheatre' href='/plays/addPlayToTheatre/" + data[i].id + "'>Dodaj</a>  " +
										"<a id='editButton"+data[i].id+"'  class='btn btn-info btn-md editTheatre' href='/theatres/editTheatre/" + data[i].id + "'>Izmeni</a></p>" +
										"<div id='quickListTheatre_"+data[i].id+"' class='btn btn-info btn-md quickListTheatre'>Karte sa popustima</div>" +
										"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
											"<button id='getHalls_" + data[i].id + "' type='button'  data-toggle='modal' data-target='#hallsDialogTheatre' class='btn btn-info getHallsBtnTheatre'>Sale</button>" +
											"<button id='addHalls_" + data[i].id + "' type='button' data-toggle='modal' data-target='#addHallDialogTheatre' class='btn btn-info addHallDialogTheatre'>+</button>" +
										"</div>" +
									"</div>";
							
							playsList = "<div style='display: none' class='playListDiv container-fluid' id='theatre"+data[i].id+"'>";
							
							playsList += "<h2>"+data[i].name+"</h2>";
							
							for(j = 0; j < data[i].plays.length; j++){
								
								play = "<div class='playDiv playClass_" + data[i].plays[j].id + "' id='playBox_" + data[i].id + "_" + data[i].plays[j].id + "'>" +
											"<h3 class='theatreName'>" + data[i].plays[j].playName + "</h3>" +
											"<p><label>Zanr: </label><span>" + data[i].plays[j].genre + "</span></p>" +
											"<p><label>Reditelj: </label><span>" + data[i].plays[j].director + "</span></p>" +
											"<p><label>Trajanje: </label><span>" + data[i].plays[j].runtime + " min</span></p>" +
											"<p><label>Glumci: </label><span>" + data[i].plays[j].actors + "</span></p>" +
											"<p><label>Opis: </label><span>" + data[i].plays[j].description + "</span></p>" +
											"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data[i].plays[j].image+"' id='ItemPreview' width='50' height='50' ></p>" +
											"<p><a class='btn btn-info btn-md deletePlay' href='/plays/deletePlay/" + data[i].plays[j].id + "'>Obrisi</a></p>" +
											"<p><a class='btn btn-info btn-md editPlay' href='/plays/editPlay/" + data[i].plays[j].id + "'>Izmeni</a></p>" +
											"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
												"<button id='getProjections_" + data[i].plays[j].id + "' type='button'  data-toggle='modal' data-target='#projectionsDialogTheatre' class='btn btn-info getProjectionsBtnTheatre'>Projekcije</button>" +
												"<button id='addProjection_" + data[i].id + "' type='button' data-toggle='modal' data-target='#addProjectionDialogTheatre' class='btn btn-info addProjectionDialogTheatre'>+</button>" +
											"</div>" +
											
										"</div>";
								
								
								
								playsList = playsList + play;
							}
							
							playsList = playsList+"</div>"
							
							$(".cinemaHolder").append(playsList).append(theatre);
						}
						

						$("#goHome").fadeIn()
						$(".cinemaHolder").fadeIn()
			});
		});
	});
	
	
	
	$(document).on('click', '.addProjectionBtnTheatre', function(){
		
		price = $('#projectionPriceTheatre').val()
		date = $('#projectionDateTheatre').val()
		time = $('#projectionTimeTheatre').val()
		playId = $('#projectionPlay').val()
		hallId = $('#hallSelectTheatre option:selected').attr('value').split('_')[1]
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0]
		
		datetime = dateFormat + " " + time
		console.log(datetime)
		
		formData = JSON.stringify({
			price: price,
			date: datetime,
			playId: playId,
			hallId: hallId
		});
		
		$.ajax({
			url: "/projections/addProjectionPlay/",
			type: "POST",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(data){
				alert("Uspesno dodata projekcija!")
			}
		})
		
		$('#hallSelectTheatre').empty();
		$('#projectionHallHolderTheatre').empty();
		
	});
	
	$(document).on('click', '.addProjectionDialogTheatre', function(){
		
		$('#hallSelectTheatre').empty();
		$('#projectionHallHolderTheatre').empty();
		
		//smestanje id filma u skriveno polje modalnog dijaloga za dodavanje projekcije
		playId = $(this).prev().attr('id').split('_')[1]
		$('#projectionPlay').val(playId)
		
		theatreId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "halls/getFromTheatre/" + theatreId
		}).then(function(data){
			option = "<option>Sala</option>"
		
			for(i = 0; i < data.length; i++){
				
				option += "<option value='hall_" + data[i].id + "'>" + data[i].hallName + "</option>"
				
			}
			$('#hallSelectTheatre').append(option);
		})
		
	});
	
	
	$(document).on('click', '.getProjectionsBtnTheatre', function(){
		

		playId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "projections/play/" + playId
		}).then(function(data){

			if(data.length === 0){
				$('#playProjectionsHolderTheatre').empty()
				$('#playProjectionsHolderTheatre').append('<p>Nema projekcija :(</p>')
			}else{
				$('#playProjectionsHolderTheatre').empty()
			}
			
			for(var i = 0; i < data.length; i++){
				
				projection = "<div class='container-fluid col-xs-12 hall'>" +
								"<p>Sala: " + data[i].hallHallName + "</p>" +
								"<p>Cena: " + data[i].price + "</p>" +
								"<p>Datum: " + data[i].date + "</p>" +
								"<p>Vreme: " + data[i].time + "</p>" +
							"</div>"
				
				$('#playProjectionsHolderTheatre').append(projection)
			}
			
		});
		
	});
	
	$(document).on('click', '.addHallBtnTheatre', function(){
		
		name = $('#hallNameTheatre').val()
		rows = $('#hallRowsTheatre').val()
		cols = $('#hallColumnsTheatre').val()
		theatreId = $('#hallTheatreId').val()
		
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
			url: "/halls/addHallTheatre/" + theatreId,
			type: "POST",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(data){
				alert("Uspesno dodata sala: " + data.hallName)
			}
		})
		
		
		
	});
	
	$(document).on('click', '.addHallDialogTheatre', function(){
		theatreId = $(this).attr('id').split('_')[1]
		console.log(theatreId)
		$('#hallTheatreId').val(theatreId);
	});
	
	$(document).on('click', '.getHallsBtnTheatre', function(){
		
		theatreId = $(this).attr('id').split('_')[1]
		
		$.ajax({
			url: "halls/getFromTheatre/" + theatreId
		}).then(function(data){
			
			if(data.length === 0){
				$('#theatreHallsHolder').empty()
				$('#theatreHallsHolder').append('<p>Nema sala :(</p>')
			}else{
				$('#theatreHallsHolder').empty()
			}
			
			
			for(var i = 0; i < data.length; i++){
				
				h = data[i]
				
				hall = "<div id='hallDiv_" + data[i].id + "' class='container-fluid col-xs-12 hall'>" + "<h4>" + data[i].hallName + "</h4>" + generateHallConf(h) + "" +
							"<a href='/halls/deleteHall/" + data[i].id + "' class='btn btn-danger deleteHallBtnTheatre'>Obrisi</a>" +
							"<button type='button' data-dismiss='modal' id='/halls/editHall/" + data[i].id + "' class='btn btn-info editHallDialogTheatreBtn'>Izmeni</a>" +
						"</div>"
				
				$('#theatreHallsHolder').append(hall)
			}
			
		});
		
	});
	
	$(document).on('click', '.editHallDialogTheatreBtn', function(){
		
		$('#editHallDialogTheatre').modal()
		
		url = $(this).attr('id')
		
		$('#editHallIdTheatre').val(url)
		$('#editHallNameTheatre').val($(this).prev().prev().prev().text())
		$('#editHallRowsTheatre').val('')
		$('#editHallColumnsTheatre').val('')
	})
	
	$(document).on('click', '.editHallBtnTheatre', function(){
		
		var hallName = $('#editHallNameTheatre').val()
		var rows = $('#editHallRowsTheatre').val()
		var cols = $('#editHallColumnsTheatre').val()
		var url = $('#editHallIdTheatre').val()
		
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
	
	$(document).on('click', '.deleteHallBtnTheatre', function(event){
		
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
	
	$(document).on('click', '.deleteTicketBtnTheatre', function(){
		
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
	
	$(document).on("click", ".quickListTheatre", function(){
		theatreId = ($(this).attr('id')).split('_')[1]
		$('#theatreHallsHolder').empty()
		getQuicksTheatre(theatreId);
	});
	
	$(document).on("click", ".createQuickTheatre", function(){
		
		event.preventDefault();
		
		createQuickFormTheatre($(this).attr("href"));
		
	});
	
	$(document).on("click", "#addQuickButtonTheatre", function(){
		
		event.preventDefault();
		
		projectionId = $('#projectionSelectTheatre option:selected').attr('value').split('_')[1]
		
		i = document.getElementById("projectionSelectTheatre").selectedIndex
		playNameTemp = document.getElementById("projectionSelectTheatre").options[i].text
		playName = playNameTemp.split('|')[0].trim();
		
		discount = $('#discountInputTheatre').val()
		theatreId = $('#theatreTicketIdTheatre').val()
		
		checkedBoxes = $('.isChecked:checkbox:checked')
		
		//			za svako sediste napraviti po kartu
		
		for(i = 0; i < checkedBoxes.length; i++){
			seatId = checkedBoxes[i].id.split('_')[1]
			
			formData = JSON.stringify({
				projectionId: projectionId,
				projectionPlayName: playName,
				discount: discount,
				projectionPlayTheatreId: theatreId,
				seatId: seatId
			});
			
			$.ajax({
				url: "http://localhost:8080/tickets/createQuick",
				type: "POST",
				contentType: "application/json",
				dataType: "json",
				data: formData,
				success: function(data){
					
					$('#addQuickFormTheatre').fadeOut()
					$('#hallHolderTheatre').empty()
					getTheatresAfterTickets()
					
				}
			})
			
		}
		
	})
	
	
	$('#projectionSelectTheatre').change(function(){
		
		if(document.getElementById("projectionSelectTheatre").selectedIndex === 0){
			$('#hallHolderTheatre').empty();
		}else{
			temp = $('#projectionSelectTheatre option:selected').attr('value')
			projId = temp.split('_')[1]
			
			getHallConfTheatre(projId)
		}
		
	})
	
	$('#hallSelectTheatre').change(function(){
		
		if(document.getElementById("hallSelectTheatre").selectedIndex === 0){
			$('#projectionHallHolderTheatre').empty();
		}else{
			temp = $('#hallSelectTheatre option:selected').attr('value')
			hallId = temp.split('_')[1]
			
			getHallProjConf(hallId)
		}
		
	})
	
	$("#goBackPlays").click(function(){
		
		$(".playListDiv").fadeOut()
		$(".theatreDiv").delay(500).fadeIn()
		
		$("#goBackPlays").fadeOut()
		$("#goHome").delay(500).fadeIn()
		
	});
	
	$("#goHome").click(function(){
		
		$('#addQuickFormTheatre').fadeOut()
		$("#goBackPlays").fadeOut()
		$("#goBackPlays").fadeOut()
		$("#goHome").fadeOut()
		$(".reportHolder").fadeOut();
		$(".cinemaHolder").fadeOut();
		$(".cinemaHolder").fadeOut();
		$(".profileHolder").fadeOut();
		$(".buttonsHolder").delay(500).fadeIn();
		
	});

	$(document).on("click",".playsListButton", function () {
		
		$(".theatreDiv").fadeOut();
		$("#theatre" + $(this).attr("id")).delay(500).fadeIn();
		$("#goBackPlays").delay(500).fadeIn();
		
	});
	
	$(document).on("click",".deletePlay", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var playBoxToDelete = $(this).parent().parent().attr("id")
		
		$.ajax({
			url: url,
			type: "DELETE",
			success: function(){
				$("#" + playBoxToDelete).remove();
			}
		});
		
	});
	
	$(document).on("click",".addPlayToTheatre", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		str = url.split("/")
		var playBoxList = "#theatre" + str[3]
		
		$("#addPlayToTheatreModal").modal("toggle")
		
		$("#addPlayToTheatre").click(function(){
			
			name = $("#nameAddTheatre").val();
			genre = $("#genreAddTheatre").val();
			director = $("#directorAddTheatre").val();
			runtime = $("#runtimeAddTheatre").val();
			description = $("#descriptionAddTheatre").val();
			actors = $("#actorsAddTheatre").val().split(",")
			
			
			var inputField = document.getElementById("imageAddTheatre");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			console.log(inputField)
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					playName: name,
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
						
						play = "<div class='playDiv' id='playBox_" + str[3] + "_" + data.id + "'>" +
									"<h3 class='theatreName'>" + data.playName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deletePlay' href='/plays/deletePlay/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editPlay' href='/plays/editPlay/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialogTheatre' class='btn btn-info getProjectionsBtnTheatre'>Projekcije</button>" +
										"<button id='addProjection_" + data.theatreId + "' type='button' data-toggle='modal' data-target='#addProjectionDialogTheatre' class='btn btn-info addProjectionDialogTheatre'>+</button>" +
									"</div>" +
								"</div>";
						
						$(playBoxList).append(play)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				
				formData = JSON.stringify({
					playName: name,
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
						
						play = "<div class='playDiv' id='playBox_" + str[3] + "_" + data.id + "'>" +
									"<h3 class='theatreName'>" + data.playName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deletePlay' href='/plays/deletePlay/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editPlay' href='/plays/editPlay/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialogTheatre' class='btn btn-info getProjectionsBtnTheatre'>Projekcije</button>" +
										"<button id='addProjection_" + data.theatreId + "' type='button' data-toggle='modal' data-target='#addProjectionDialogTheatre' class='btn btn-info addProjectionDialogTheatre'>+</button>" +
									"</div>" +
								"</div>";
						
						$(playBoxList).append(play)
						
					}
				});
			}
			
			$("#addPlayToTheatreModal").modal("toggle")
			
		});
		
	});
	
	$(document).on("click",".editPlay", function () {
		
		event.preventDefault();
		
		var url = $(this).attr("href")
		var playClass = $(this).parent().parent().attr("class").split(" ")[1] //  klasa filma koji smo editovali da bi se promenio svaki film na stranici
		var theatreId = $(this).parent().parent().attr("id").split("_")[1]
		
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
		
		$("#nameEditTheatre").val(name)
		$("#genreEditTheatre").val(genre)
		$("#directorEditTheatre").val(director)
		$("#runtimeEditTheatre").val(runtime.split(" ")[0])
		$("#actorsEditTheatre").val(actors)
		$("#descriptionEditTheatre").val(description)
		
		
		$("#editPlayModal").modal("toggle")
		
		$("#editPlay").click(function(){
			
			name = $("#nameEditTheatre").val();
			genre = $("#genreEditTheatre").val();
			director = $("#directorEditTheatre").val();
			runtime = $("#runtimeEditTheatre").val();
			description = $("#descriptionEditTheatre").val();
			actors = $("#actorsEditTheatre").val().split(",")
			
			var inputField = document.getElementById("imageEditTheatre");
			var imageFile = inputField.files[0];
			var reader = new FileReader();
			
			reader.onloadend = function() {
				
				formData = JSON.stringify({
					playName: name,
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
						
						play = "<div class='playDiv' id='playBox_" + data.theatreId + "_" + data.id + "'>" +
									"<h3 class='theatreName'>" + data.playName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deletePlay' href='/plays/deletePlay/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editPlay' href='/plays/editPlay/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialogTheatre' class='btn btn-info getProjectionsBtnTheatre'>Projekcije</button>" +
										"<button id='addProjection_" + data.theatreId + "' type='button' data-toggle='modal' data-target='#addProjectionDialogTheatre' class='btn btn-info addProjectionDialogTheatre'>+</button>" +
									"</div>" +
								"</div>";
						
						$("."+playClass).replaceWith(play)
						
					}
				});
			  }
			if(imageFile !== undefined)
				reader.readAsDataURL(imageFile);
			else{
				formData = JSON.stringify({
					playName: name,
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
						
						play = "<div class='playDiv' id='playBox_" + data.theatreId + "_" + data.id + "'>" +
									"<h3 class='theatreName'>" + data.playName + "</h3>" +
									"<p><label>Zanr: </label><span>" + data.genre + "</span></p>" +
									"<p><label>Reditelj: </label><span>" + data.director + "</span></p>" +
									"<p><label>Trajanje: </label><span>" + data.runtime + " min</span></p>" +
									"<p><label>Glumci: </label><span>" + data.actors + "</span></p>" +
									"<p><label>Opis: </label><span>" + data.description + "</span></p>" +
									"<p><label>Slika: </label><img src='data:image/(png|jpg|jpeg|gif|bmp|tiff);base64, "+data.image+"' id='ItemPreview' width='50' height='50' ></p>" +
									"<p><a class='btn btn-info btn-md deletePlay' href='/plays/deletePlay/" + data.id + "'>Obrisi</a></p>" +
									"<p><a class='btn btn-info btn-md editPlay' href='/plays/editPlay/" + data.id + "'>Izmeni</a></p>" +
									"<div class='btn-group' role='group' style='margin-left: 6px;'>" +
										"<button id='getProjections_" + data.id + "' type='button'  data-toggle='modal' data-target='#projectionsDialogTheatre' class='btn btn-info getProjectionsBtnTheatre'>Projekcije</button>" +
										"<button id='addProjection_" + data.theatreId + "' type='button' data-toggle='modal' data-target='#addProjectionDialogTheatre' class='btn btn-info addProjectionDialogTheatre'>+</button>" +
									"</div>" +
								"</div>";
						
						$("."+playClass).replaceWith(play)
						
					}
				});
			}
			
			$("#editPlayModal").modal("toggle")
			
		});
		
	});
	
	$(document).on("click",".editTheatre",function(){
		
		event.preventDefault();
		
		url = $(this).attr("href")
		
		nameToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[0] ).attr("id")
		addressToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[1].children[1] ).attr("id")
		descriptionToReplace = "#" + $( $("#" + $(this).attr("id")).siblings()[2].children[1] ).attr("id")
		
		$("#theatreNameEdit").val($(nameToReplace).text())		 //ime u polje
		$("#theatreAddressEdit").val($(addressToReplace).text())		 //adresa
		$("#theatreDescriptionEdit").val($(descriptionToReplace).text())		 //opis
		
		$("#editTheatreModal").modal("toggle")
		
		$("#editTheatre").click(function(){
			
			name = $("#theatreNameEdit").val()
			address = $("#theatreAddressEdit").val()
			description = $("#theatreDescriptionEdit").val()

			
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
					
					$(nameToReplace).replaceWith("<h3 class='theatreName' id='theatreName" + data.id + "'>" + data.name + "</h3>")
					$(addressToReplace).replaceWith("<span id='theatreAddress" + data.id + "'>" + data.address + "</span>")
					$(descriptionToReplace).replaceWith("<span id='theatreDesc" + data.id + "'>" + data.description + "</span>")
					
				}
			});
			
			$("#editTheatreModal").modal("toggle")
		});
		
	});
	
});