var urlBase = "http://localhost:8080/";

function getUsers(){
	$.ajax({
		method : 'GET',
		url : urlBase + "admin/users",
		success : function(data){
			for (i = 0; i < data.length; i++) {
				
				appendUser(data[i]);
				
			}
				
		},
		error : function(xhr, status, error) {
			console.log(status + error);
			alert(error);
		}
	});
}
function getCinemas(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "admin/cinemas",
		success : function(data){
			for (i = 0; i < data.length; i++) {
				
				appendCinema(data[i]);
				
			}
				
		},
		error : function(xhr, status, error) {
			console.log(status + error);
			alert(error);
		}
	});
	
}
function getTheatres(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "admin/theatres",
		success : function(data){
			for (i = 0; i < data.length; i++) {
				
				appendTheatre(data[i]);
				
			}
				
		},
		error : function(xhr, status, error) {
			console.log(status + error);
			alert(error);
		}
	});
	
}
function getHalls(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "admin/halls",
		success : function(data){
			for (i = 0; i < data.length; i++) {
				
				
			}
				
		},
		error : function(xhr, status, error) {
			console.log(status + error);
			alert(error);
		}
	});
	
}
function addCinema(){
	
}
function addTheatre(){
	
}
function addHall(){
	
}
function appendUser(data){
		
	var row = "	<tr id=\""+data.id+"\">"
			  	+"<td id=\"name\">"+data.name+"</td>"
			  	+"	<td id=\"surname\">"+data.surname+"</td>"
			  	+"	<td id=\"email\">"+data.email+"</td>"
			  	+"	<td id=\"phone\">"+data.phoneNumber+"</td>"
			  	+"	<td id=\"city\">"+data.city+"</td>"
			  	+"	<td id=\"role\">"+data.role+"</td>"
			  	+"	<td id=\"buttons\">"
			  	+"		<div style=\"float : right\">"
			  	+"			<a id=\""+data.id+"\" class=\"btn btn-warning btn-sm\" >Izmeni Ulogu</a>"
			  	+"			<a id=\""+data.id+"\" class=\"btn btn-danger btn-sm\" >Ukloni</a>"
			  	+"		</div>"
			  	+"	</td>"
			  	+"	<td id=\"userId\" style=\"visibility:hidden;\">"+data.id+"</td>"
				+"</tr>";
	$('#usersTable').append(row);
}
function appendCinema(data){
	var row = 	"<tr id=\""+data.id+"\">"
				+"	<td id=\"cinemaName\">"+data.name+"</td>"
				+"	<td id=\"cinemaDescription\">"+data.description+"</td>"
				+"	<td id=\"cinemaAdress\">"+data.address+"</td>"
				+"	<td id=\"cinemaRating\">"+data.avgRating+"</td>"
				+"	<td id=\"cinemaAdmin\">ADMIN</td>"
				+"	<td><div class=\"btn-group\">"
				+"	  <button type=\"button\" class=\"btn dropdown-toggle\" data-toggle=\"dropdown\">Sale"					
				+"	  </button>"
				+"	  <ul class=\"dropdown-menu\" role=\"menu\">"
				+"	    <table id=\"cinemaHallsTable\" class=\"table\">"
				+"	    	<tr>"
				+"	        	<th>Naziv</th>"
				+"	            <th>Broj mesta</th>"
				+"	        </tr>"				
				+"		</table>"
				+"	  </ul>"
				+"	</div>"
				+"</td>"
				+"	<td id=\"cinemaButtons\">"
				+"		<div style=\"float : right\">"
				+"			<a id=\""+data.id+"\"  class=\"btn btn-warning btn-sm\" >Izmeni</a>"
				+"			<a id=\""+data.id+"\" class=\"btn btn-danger btn-sm\" >Ukloni</a>"
				+"		</div>"
				+"	</td>"
				+"	<td id=\"cinemaId\" style=\"visibility:hidden;\">"+data.id+"</td>"
				+"</tr>";
	$('#cinemaTable').append(row);
}
function appendHall(data, place){
	var seats = data.row * data.cols;
	var row = 	"<tr>"
				+"	<td id=\"hallName\">"+data.name+"</td>"
				+"	<td id=\"hallSeats\">"+seats+"</td>"
				+"	<td id=\"hallId\">"+data.id+"</td>"
				+"</tr>";
	if(place == "cinema"){
		
	}
	
}
function appendTheatre(data){
	var row = 	"<tr id=\""+data.id+"\">"
				+"	<td id=\"theatreName\">"+data.name+"</td>"
				+"	<td id=\"theatreDescription\">"+data.description+"</td>"
				+"	<td id=\"theatreAdress\">"+data.address+"</td>"
				+"	<td id=\"theatreRating\">"+data.avgRating+"</td>"
				+"	<td id=\"theatreAdmin\">ADMIN</td>"
				+"	<td id=\"theatreButtons\">"
				+"		<div style=\"float : right\">"
				+"			<a id=\""+data.id+"\"  class=\"btn btn-warning btn-sm\" >Izmeni</a>"
				+"			<a id=\""+data.id+"\" class=\"btn btn-danger btn-sm\" >Ukloni</a>"
				+"		</div>"
				+"	</td>"
				+"	<td id=\"theatreId\" style=\"visibility:hidden;\">"+data.id+"</td>"
				+"</tr>";
	$('#theatreTable').append(row);
}
