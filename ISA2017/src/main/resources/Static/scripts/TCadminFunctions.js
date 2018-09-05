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
				"<a class='btn btn-danger deleteTicketBtn' href='" + data[i].id + "'>Obrisi</a>"
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
			
			option += "<option value='proj_" + data[i].id + "'>" + data[i].movieName + " | " + data[i].date + "</option>"
			
		}
		$('#projectionSelect').append(option);
		
	});
	
	// discount string
	// cinemaId je argument funkcije i vec je unesen i disabled polje
	
}

function getHallConf(projId){
	
	$('#hallHolder').empty()
	
	$.ajax({
		url: "halls/inProjection/" + projId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.name + "</h4>"
		
		$('#hallHolder').append(hallName).append(table)
		
	})
}

function getHallProjConf(hallId){
	
	$('#hallHolder').empty()
	
	$.ajax({
		url: "halls/hall/" + hallId
	}).then(function(data){
		
		table = generateHallConf(data)
		
		hallName = "<h4>Sala: " + data.name + "</h4>"
		
		$('#projectionHallHolder').append(hallName).append(table)
		
	})
}

function generateHallConf(data){
	
	seatsNumber = data.seats.length - 1
	rows = data.seats[seatsNumber].seatRow
	columns = data.seats[seatsNumber].seatColumn
	
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