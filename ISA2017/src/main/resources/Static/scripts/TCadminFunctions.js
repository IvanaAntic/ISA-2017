function getQuicks(cinemaId){
	
	$('.cinemaHolder').empty();
	
	addNew = "<div><a class='btn btn-lg btn-primary createQuick' href='" + cinemaId + "'>Dodaj</a></div>"
	$(".cinemaHolder").append(addNew);
	
	$.ajax({
		url: "/tickets/getAllCinema/"+cinemaId
	}).then(function(data){
		
		for(i = 0; i < data.length; i++){
			
			ticket = "<div class='cinemaDiv col-md-6' id=ticket_" + data[i].id + ">" +
				"<h2>" + data[i].projectionMovieName + "</p>" +
				"<p>Cena: " + data[i].projectionPrice + "</p>" +
				"<p>Popust: " + data[i].discount + "%</p>" +
				"<p>Datum projekcije: " + data[i].date + "</p>" +
				"<p>Vreme projekcije: " + data[i].time + "</p>" +
				"<p>Sala: " + data[i].projectionHallName + "</p>" +
				"<p>Red: " + data[i].seatRowNumber + "</p>" +
				"<p>Kolona: " + data[i].seatColumnNumber + "</p>" +
				"<a class='btn btn-danger deleteTicketBtn' href='" + data[i].id + "'>Obrisi</a>"
			"</div>";
			
			$(".cinemaHolder").append(ticket);
			
		}
		
	});
	
}

function getQuicksTheatre(cinemaId){
	
	$('.cinemaHolder').empty();
	
	addNew = "<div><a class='btn btn-lg btn-primary createQuickTheatre' href='" + cinemaId + "'>Dodaj</a></div>"
	$(".cinemaHolder").append(addNew);
	
	$.ajax({
		url: "/tickets/getAllTheatre/"+cinemaId
	}).then(function(data){
		
		for(i = 0; i < data.length; i++){
			
			ticket = "<div class='cinemaDiv col-md-6' id=ticket_" + data[i].id + ">" +
				"<h2>" + data[i].projectionPlayName + "</p>" +
				"<p>Cena: " + data[i].projectionPrice + "</p>" +
				"<p>Popust: " + data[i].discount + "%</p>" +
				"<p>Datum projekcije: " + data[i].date + "</p>" +
				"<p>Vreme projekcije: " + data[i].time + "</p>" +
				"<p>Sala: " + data[i].projectionHallName + "</p>" +
				"<p>Red: " + data[i].seatRowNumber + "</p>" +
				"<p>Kolona: " + data[i].seatColumnNumber + "</p>" +
				"<a class='btn btn-danger deleteTicketBtnTheatre' href='" + data[i].id + "'>Obrisi</a>"
			"</div>";
			
			$(".cinemaHolder").append(ticket);
			
		}
		
	});
	
}

function createQuickForm(cinemaId){
	
	$('.cinemaHolder').empty().clone($('#addQuickForm'));
	$('#addQuickForm').show();
	$('#projectionSelect').empty();
	$('#hallHolder').empty();
	document.getElementById("quickForm").reset();
	
	$('#cinemaTicketId').val(cinemaId)
	
	$.ajax({
		url: "/projections/" + cinemaId
	}).then(function(data){
		
		option = "<option>Projekcija</option>"
		
		for(i = 0; i < data.length; i++){
			
			option += "<option value='proj_" + data[i].id + "'>" + data[i].movieMovieName + " | " + data[i].date + "</option>"
			
		}
		$('#projectionSelect').append(option);
		
	});
	
}

function createQuickFormTheatre(cinemaId){
	
	$('.cinemaHolder').empty().clone($('#addQuickFormTheatre'));
	$('#addQuickFormTheatre').show();
	$('#projectionSelectTheatre').empty();
	$('#hallHolderTheatre').empty();
	document.getElementById("quickFormTheatre").reset();
	
	$('#theatreTicketId').val(cinemaId)
	
	$.ajax({
		url: "/projections/theatre/" + cinemaId
	}).then(function(data){
		
		option = "<option>Projekcija</option>"
		
		for(i = 0; i < data.length; i++){
			
			option += "<option value='proj_" + data[i].id + "'>" + data[i].playPlayName + " | " + data[i].date + "</option>"
			
		}
		$('#projectionSelectTheatre').append(option);
		
	});
	
}

function getHallConf(projId){
	
	$('#hallHolder').empty()
	
	$.ajax({
		url: "halls/inProjection/" + projId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.hallName + "</h4>"
		
		$('#hallHolder').append(hallName).append(table)
		
	})
}

function getHallConfTheatre(projId){
	
	$('#hallHolderTheatre').empty()
	
	$.ajax({
		url: "halls/inProjection/" + projId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.hallName + "</h4>"
		
		$('#hallHolderTheatre').append(hallName).append(table)
		
	})
}

function getHallProjConf(hallId){
	
	$('#hallHolder').empty()
	
	$.ajax({
		url: "halls/hall/" + hallId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.hallName + "</h4>"
		
		$('#projectionHallHolder').append(hallName).append(table)
		
	})
}

function getHallProjConfTheatre(hallId){
	
	$('#hallHolderTheatre').empty()
	
	$.ajax({
		url: "halls/hall/" + hallId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.hallName + "</h4>"
		
		$('#projectionHallHolderTheatre').append(hallName).append(table)
		
	})
}

function getHallProjConfUser(projId){
	
	$('#showSeats').empty()
	
	$.ajax({
		url: "/halls/inProjection/" + projId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<input type='hidden' class='hiddenfieldclass' value='"+projId+"'></input><h4>Sala: " + data.hallName + "</h4>"
		
		$('#showSeats').append(hallName).append(table)
		
	})
}
function generateHallConf(data){
	console.log("GenerateHallConf" + data)
	seatsNumber = data.seats.length - 1
	rows = data.seats[seatsNumber].rowNumber
	columns = data.seats[seatsNumber].columnNumber
	
	table = "<table>"
	
	for(var i = 0; i < rows+1; i++){
		
		table += "<tr><th>" + i + "</th>"
		
		for(j = 1; j <= columns; j++){
			
			if(i===0){
				table += "<th>" + j + "</th>"
			}else{
				
				myRow = i-1;
				myColumn = j-1;
				seatNumber = (myRow*columns) + myColumn;
				
				if(data.seats[seatNumber].reserved === true){
					table += "<td><input type='checkbox' class='isChecked' id='seat_" + data.seats[seatNumber].id + "' disabled></td>"
				}else{
					table += "<td><input type='checkbox' class='isChecked' id='seat_" + data.seats[seatNumber].id + "'></td>"
				}
				
			}
			
		}
		
		table += "</tr>"
		
	}
	
	table += "</table>"
		
	return table
	
}

function getCinemasAfterTickets(){
	
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
	
}

function getTheatresAfterTickets(){
	
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
	
}