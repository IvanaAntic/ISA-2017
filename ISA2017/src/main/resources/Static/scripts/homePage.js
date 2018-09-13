$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	loadFriendRequests();
	loadVisitHistory();
	loadFriendAccepted();
	loadPeople();
	loadTheatre();
	loadCinema();
	$("#izmeni").click(function(){
		console.log("desilo se");
		userEdit();
		
	});
	$(".searchPozoriste").click(function(){
		
		console.log("search");
	
	});
	
	
	
	
	
	$(".navbar-right").on('click', function (e) {
	    
	    $.ajax({
	    	type:'GET',
	    	contentType: "application/json",
			datatype: 'json',
			url:"http://localhost:8080/user/loggoutUser",
			success: function(){
				 sessionStorage.removeItem('logged');
				 window.location.href='/';
			 },
				error:function(error){
					console.log("e"+error);
				}
		});
	    
	});
});

function loadUser(){
	
	$.ajax({
		
		type:'GET',
		url:"http://localhost:8080/user/displayUser",
		contentType: "application/json",
		datatype: 'json',
		success:function(data){
			
			  $(".name").html(" " + data.name);
			  $(".surname").html(" " + data.surname);
			  $(".email").html(" " + data.email);
			  $(".phoneNumber").html(" " + data.phoneNumber);
			  $(".city").html(" " + data.city);
			  
			  $(".nameEdit").val(data.name);
			  $(".surnameEdit").val(data.surname);
			  $(".passwordEdit").val(data.password);
			  $(".townEdit").val(data.city);
			  $(".phoneNumberEdit").val(data.phoneNumber);
			  
		},
		error:function(error){
			
		}
		
	})
}

function userEdit(){
	
	formData= JSON.stringify({
		
		name: $("#inputForm [name='nameEdit']").val(),
		surname: $("#inputForm [name='surnameEdit']").val(),
		city: $("#inputForm [name='townEdit']").val(),
		phoneNumber: $("#inputForm [name='phoneNumberEdit']").val(),
          });
	$.ajax({
		type:"POST",
		url:"http://localhost:8080/user/editUser",
		contentType: "application/json",
		datatype: "json",
		data:formData,
		success:function(data){
			 alert("Profile data changed successfully! ");
			 loadUser();
		}
		
	});
	
}

function loadFriendRequests(){
	console.log("loadFriendRequests");
	$.ajax({
			url:"http://localhost:8080/friendship/displayFriendRequests",
			method:"GET",
			contentType: "application/json",
			datatype: 'json',
			success: function(data){
				$(".reqTable").empty();
				for(i=0;i<data.length;i++){
					newRow="<tr>"
						+"<td>"+data[i].name+"</td>"
						+"<td>"+data[i].surname+"</td>"
						+"<td><button name='accept' class='btn btn-primary accept' style='width:80px;' id=" + data[i].id + ">Accept</button></td>"
						+"<td><button name='delete' class='btn btn-danger rejectFR' style='width:80px;' id=" + data[i].id+ ">Reject</button></td>>"
						+"</tr>";
					$(".reqTable").append(newRow);
				}
			}
	});
}
function loadFriendAccepted(){
	console.log("loadFriendAccepted");
	$.ajax({
		url:"http://localhost:8080/friendship/displayFriendAccepted",
		method:"GET",
		contentType: "application/json",
		datatype: 'json',
		success: function(data){
			$(".friendsTable").empty();
			for(i=0;i<data.length;i++){
				newRow="<tr>" +
						"<td>"+data[i].name+"</td>"
						+"<td>"+data[i].surname+"</td>"
						+"<td><button name='delete' class='btn btn-danger deleteFromFriend' style='width:160px;' id=" + data[i].id+ ">Remove from friends</button></td>>"
						+"</tr>";
				$(".friendsTable").append(newRow);
			}
		}
	});
}

function loadVisitHistory(){
	
	$('#historyHolder').empty()
	
	console.log("getting visit history...")
	
	$.ajax({
		url: "tickets/getHistory"
	}).then(function(data){
		
		for(var i = 0; i < data.length; i++){
			
			movie = "<li class='list-group-item'>" + data[i].projectionMovieName + " <a id='rateMovie_" + data[i].projectionMovieId + "' class='badge progress-bar-info rateMovieCinema' href='movies/rateMovie/" + data[i].projectionMovieId + "'>Oceni</a>" +
					"<span class='badge progress-bar-warning'>Film</span></li>"
			cinema = "<li class='list-group-item'>" + data[i].projectionMovieCinemaName + " <a id='rateCinema_" + data[i].projectionMovieId + "' class='badge progress-bar-info rateMovieCinema' href='cinemas/rateCinema/" + data[i].projectionMovieCinemaId + "'>Oceni</a>" +
					"<span class='badge progress-bar-success'>Bioskop</span></li>"
			
			$("#historyHolder").append(movie).append(cinema);
			
		}
		
	});
	
}

$(document).on('click', '.rateMovieCinema', function(event){
	
	event.preventDefault();
	
	$('#rateMovieCinemaDialog').modal()
	
	$('#rateName').text($(this).parent().text().split('Oceni')[0])
	$('#rateId').val($(this).attr('href'))
	$('#movieCinemaIdRate').val($(this).attr('id'))
	
});

$(document).on('click', '#rateMovieCinema', function(){
	
	rateId = $('#rateId').val()
	mark = $('#mark').val();
	thisId = $('#movieCinemaIdRate').val()
	
	formData = JSON.stringify({
		rating: mark
	})
	
	$.ajax({
		url: rateId,
		type: "POST",
		contentType: "application/json",
		datatype: 'json',
		data: formData,
		success: function(){
			$("#" + thisId).text('Ocenjeno')
			$("#" + thisId).removeClass('progress-bar-info').addClass('progress-bar-success')
		},
		error: function(){
			alert("Neuspesno!")
		}
		
	})
	
});

$(document).on("click",".deleteFromFriend", function(event){
	console.log("NISMO VISE PRIJATELJI");
	 var id = $(this).attr('id');
     formData= JSON.stringify({
 		id: id
           });
	$.ajax({
		url:"http://localhost:8080/friendship/delete",
		method:"DELETE",
		data : formData,
		contentType : 'application/json',
    	success: function(){
    			console.log("RASKINUTO PRIJATELJSTVO");
    			loadFriendAccepted();
        		loadFriendRequests();
        		loadPeople();
    	}
	});
});
$(document).on("click",".accept",function(event){
	//obostrano je prijateljsnto ako se klikne na accept
	console.log("hocemo da prihvatimo prijatelja");
	 var id = $(this).attr('id');
     formData= JSON.stringify({
 		id: id
           });
     console.log(id);
     console.log(formData);
	$.ajax({
		url: "http://localhost:8080/friendship/accept",
		method: "POST",
		data : formData,
		contentType : 'application/json',
    	success: function(){
    			console.log("Prihvaceno prijateljstvo");
    			loadFriendAccepted();
        		loadFriendRequests();
        		loadPeople();
    			
    	}
	});
	
});


$(document).on("click",".rejectFR",function(event){
	console.log("Pritisnuto dugme REJECT friend");
	var id = $(this).attr('id');
    formData= JSON.stringify({
		id: id
          });
    $.ajax({
    	url:"http://localhost:8080/friendship/reject",
    	method:"DELETE",
    	data:formData,
    	contentType: 'application/json',
    	success: function(){
    		console.log("REJECT FRIEND");
    		loadFriendAccepted();
    		loadFriendRequests();
    		loadPeople();
    	}
    });
});

function loadPeople(){
	$.ajax({
		 url: "http://localhost:8080/user/displayUsers",
		method:"GET",
		success: function(data){
			  $(".peopleTable").empty();
			  displayListOfPeople(data);
		}
	});
}

function displayListOfPeople(data){
	console.log(data);
	for(i=0;i<data.length;i++){
		newRow="<tr>"
			+"<td>"+data[i].name+"</td>"
			+"<td>"+data[i].surname+"</td>";
		if(data[i].type==="0"){ //are not friends
			newRow+="<td><button name='add'  class='btn btn-success dodaj' style='width:80px;' id=" + data[i].id + ">Add</button></td>";
		}else if(data[i].type==="1"){ //waiting
			newRow+="<td><button name='pending' class='btn btn-warning' style='width:80px;' disabled>Pending</button></td>";
		}else if(data[i].type==="2"){//allready friends
			newRow+="<td><input name='delete' value='Delete' class='btn btn-danger obrisi' style='width:80px;'  id=" + data[i].id + "></input></td>";
		}
		newRow+="</tr>";
		$(".peopleTable").append(newRow);
	}
	
}

$(document).on('click','.dodaj',function(event){
	 var retVal = confirm("Are you sure you want to add a friend?");
	 if( retVal === true ){
	        var id = $(this).attr('id');
	        formData= JSON.stringify({
	    		id: id
	              });
	        console.log(id);
	        console.log(formData);
	        $.ajax({
	        	url: "http://localhost:8080/friendship/addFriend",
	        	method: "POST",
	        	data : formData,
	        	contentType : 'application/json',
	        	success: function(){
	        			console.log("USPesnoste dodaliii Prijatelja");
	        			 $("#"+id).removeClass('btn btn-success dodaj').addClass('btn btn-warning');
	        			 $("#"+id).text('Pending');
	        			 $("#"+id).prop('disabled', true);
	        	},
	        error:function(){
    			console.log("Vec ste dodali za prijatelja");
        	}
		
	        });
	  }
	
});

$(document).on("click",".obrisi",function(event){
	 var retVal = confirm("Are you sure you want to remove from friends?");
	 if( retVal === true ){
		 
	        var ido = $(this).attr('id');
	      
	      
	        formData= JSON.stringify({
	    		id: ido
	         });
	       // console.log("id"+thisId);
	        console.log("formData"+formData);
	        $.ajax({
	    		url:"http://localhost:8080/friendship/delete",
	    		method:"DELETE",
	    		data : formData,
	    		contentType : 'application/json',
	        	success: function(){
	        			console.log("RASKINUTO PRIJATELJSTVO");
	        			//$(".obrisi").removeClass('btn btn-danger obrisi').addClass('btn btn-success dodaj');
	        			//$("input").attr("value","Add");
	        			loadFriendAccepted();
	            		loadFriendRequests();
	            		loadPeople();
	        			
	        	}
	    	});
	   }
});

$(document).on("click",".sortNameAZ",function(event){
	console.log("pressed sortNameAY");
	$.ajax({
		url:"http://localhost:8080/user/sortByName",
		method:"POST",
		 dataType: 'json',
		 success:function(data){
			 console.log("ok sortname");
			 $(".peopleTable").empty();
	         displayListOfPeople(data);
		 }
	});
});

$(document).on("click",".sortSurnameAZ",function(event){
	console.log("pressed sortSurnameAZ");
	$.ajax({
		url:"http://localhost:8080/user/sortBySurname",
		method:"POST",
		 dataType: 'json',
		 success:function(data){
			 console.log("ok sortname");
			 $(".peopleTable").empty();
	        displayListOfPeople(data);
		 }
	});
});

function loadTheatre(){
	console.log("Ucitavanje");
	$.ajax({
		url:"http://localhost:8080/theatres/getTheatres",
		method:"GET",
		contentType: 'application/json',
        success: function (data){
        	console.log("ok");
        	console.log(data);
        	for(i=0;i<data.length;i++){
        		newRow="<tr>"
        			+"<td>"+data[i].name+"<td>"
        			+"<td>"+data[i].address+"<td>"
        			+"<td>"+data[i].description+"<td>"
        			+"<td>"+data[i].avgRating+"<td>"
        			+'<td><button type="button" class="btn btn-success repertoar" data-toggle="modal" data-target="#showTheatreForRepertoar" >Repertoar</button><td>'
        			+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
        			+"</tr>";
        		$(".theatreTable").append(newRow);
        		
        	}
        }
	});
	
}

function loadCinema(){
	console.log("Ucitavanje Cinema");
	$.ajax({
		url:"http://localhost:8080/cinemas/getCinemas",
		method:"GET",
		contentType: 'application/json',
        success: function (data){
        	console.log(data);
        	for(i=0;i<data.length;i++){
        		newRow="<tr>"
        			+"<td>"+data[i].name+"<td>"
        			+"<td>"+data[i].address+"<td>"
        			+"<td>"+data[i].description+"<td>"
        			+"<td>"+data[i].avgRating+"<td>"
        			+'<td><button type="button" class="btn btn-success repertoarCinema" data-toggle="modal" data-target="#showCinemaForRepertoar" >Repertoar</button><td>'
        			+" <td style=\"display:none;\" id=\"id\" type=\"hidden\" name=\"id\" class=\"id\">"+data[i].id+"</td>"
        			+"</tr>";
        		$(".cinemaTable").append(newRow);
        		
        	}
        }
	});
	
}

$(document).on("click",".repertoarCinema",function(event){
	console.log("Ucitavanje Cinema REPERTOAR");
	console.log("stisli");
	tr_parent = $(this).closest("tr");
	$('#id').val(tr_parent.find(".id").html());
	var id = $('#id').val();
	console.log(id);
	$.ajax({
		url:"http://localhost:8080/cinemas/getCinemas",
		method:"GET",
		contentType: 'application/json',
        success: function (data){
        	$(".cinemaTableShow").empty();
        	console.log(data);
        	for(i=0;i<data.length;i++){
        	if(data[i].id==id){ 
        		for(j = 0; j < data[i].movies.length; j++){
        			newRow= "<div class='movieDiv movieClass_" + data[i].movies[j].id + "' id='movieBox_" + data[i].id + "_" + data[i].movies[j].id + "'>"+ 
				"<h3 class='cinemaName'>" + data[i].movies[j].movieName + "</h3>" +
				"<p><label>Zanr: </label><span>" + data[i].movies[j].genre + "</span></p>" +
				"<p><label>Reditelj: </label><span>" + data[i].movies[j].director + "</span></p>" +
				"<p><label>Trajanje: </label><span>" + data[i].movies[j].runtime + " min</span></p>" +
				"<p><label>Glumci: </label><span>" + data[i].movies[j].actors + "</span></p>" +
				"<p><label>Opis: </label><span>" + data[i].movies[j].description + "</span></p>"+
				"<button id='getProjections_" + data[i].movies[j].id + "' type='button'  data-toggle='modal' data-target='#projectionsDialog' class='btn btn-info getProjectionsBtn'>Projekcije</button></div>";
        		$(".cinemaTableShow").append(newRow);
        		}
        	}
        	}
        }
	 });
});



$(document).on("click",".repertoar",function(event){
	console.log("stisli");
	tr_parent = $(this).closest("tr");
	$('#id').val(tr_parent.find(".id").html());
	var id = $('#id').val();
	console.log(id);
	$.ajax({
		url:"http://localhost:8080/theatres/getTheatres",
		method:"GET",
		contentType: 'application/json',
        success: function (data){
        	console.log("ok");
        	console.log(data);
        	for(i=0;i<data.length;i++){
        		
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
							
						//playsList = playsList + play;
						$(".theatreTableShow").append(play);
					}
        		//$(".theatreTable").append(newRow);
        		
        	}
        }
	});
});

$(document).on("click",".getProjectionsBtn",function(event){
	//$(".cinemaTable").hide();
	$("#showCinemaForRepertoar").modal('hide');
	movieId = $(this).attr('id').split('_')[1]
	$.ajax({
		url: "projections/movie/" + movieId
	}).then(function(data){
		$('.projectionTableShow').empty()
		if(data.length === 0){
			$('.projectionTableShow').empty()
			$('.projectionTableShow').append('<p>Nema projekcija :(</p>')
		}else{
			$('#movieProjectionsHolder').empty()
		}
		
		for(var i = 0; i < data.length; i++){
			
			projection = "<div class='container-fluid col-xs-12 hall'>" +
							"<p>Sala: " + data[i].hallName + "</p>" +
							"<p>Cena: " + data[i].price + "</p>" +
							"<p>Datum: " + data[i].date + "</p>" +
							"<p>Vreme: " + data[i].time + "</p>" +
						"</div>"
			
			$('.projectionTableShow').append(projection)
		}
		
	});
	
	
	
});