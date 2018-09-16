$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	loadFriendRequests();
	loadVisitHistory();
	loadFriendAccepted();
	loadPeople();
	loadTheatre();
	loadCinema();
	loadUpcomingReservations();
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
	$('#tableReserve').empty();
	$('#tableProjection').empty();

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
        			+'<td><button type="button" class="btn btn-success repertoarCinema" data-toggle="modal">Repertoar</button><td>'
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
	
	 $('#tabs a[href="#rezervacije"]').tab('show');
	
	$.ajax({
		url:"http://localhost:8080/cinemas/getCinemas",
		method:"GET",
		contentType: 'application/json',
        success: function (data){
        	$('#tableProjection').empty()
        	$("#tableReserve").empty();
        	$("#tableHall").empty();
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
				"<button id='getProjections_" + data[i].movies[j].id + "' type='button'  class='btn btn-info getProjectionsBtn'>Projekcije</button></div>";
        		$("#tableReserve").append(newRow);
        		}
        	}
        	}
        }
	 });
});


//THEATRE
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

//CINEMA BUTNON
$(document).on("click",".getProjectionsBtn",function(event){
	//$(".cinemaTable").hide();
	//$("#showCinemaForRepertoar").modal('hide');
	
	movieId = $(this).attr('id').split('_')[1]
	$.ajax({
		url: "projections/movie/" + movieId
	}).then(function(data){
		$('#tableProjection').empty()
		$('#tableReserve').empty()
		if(data.length === 0){
			$('#tableProjection').empty()
			$('#tableProjection').append('<p>Nema projekcija :(</p>')
		}else{
			$('#tableReserve').empty()
		}
		
		for(var i = 0; i < data.length; i++){
			
			projection = "<tr>" +
							"<td id='sala' class='sala'>" +
							"<label>Name</label>"+
							"<p id='hallName'>"+ data[i].hallName +"</p>"+
							"</td>"+
							"<td class='price'>" +
							"Cena: " + data[i].price +
							" </td>" +
							"<td class='projectionDate'>" +
							"<label>Datum</label> "+
							"<p id='dateStart'>"+ data[i].date + "</p>"+
							" </td>" +
							"<td class='projectionTime'>" +
							"<label>Vreme</label>"+
							"<p id='time'>"+data[i].time +  "</p>"+
							" </td>" +
							"<td><div id='getSala_"+data[i].id+"' class='btn btn-info btn-md cinemaSeatButtonNext'>NEXT</div></td>"+
						"</tr>"
			
			$('#tableProjection').append(projection)
		}
		
	});
});


$(document).on("click",".cinemaSeatButtonNext",function(event){
	//ovo vraca projekciju i proverava da li je moguce reyervisati
	hallId = $(this).attr('id').split('_')[1];
	//danasnji datum
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd<10) {
	    dd = '0'+dd
	} 

	if(mm<10) {
	    mm = '0'+mm
	} 

	today = dd + '/' + mm + '/' + yyyy;
	console.log("novo"+today)
	//kraj 
	//trenutno vreme
	var currentTime = new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
	console.log("myDate"+ currentTime);

	//ne nego mi daj jednu projekcijuuu
	$.ajax({
		url: "projections/hall/" + hallId
	}).then(function(data){
		//Sta ovde mozemo vratiti? seat u toj sali i onda u novi div ispisemo ta mesta
		if(data.id==null){
			alert("Ima manje od 30 minuta do pocetka projekcija. Ne mozete rezervisati")
		}
		//vrati tu projekciju u projekciji imas salu
		else{ 
		console.log("Hall podaci ubaci novi div");
		console.log("sta smo vratili na front"+data);
		console.log("id onog na fronut projekcije:"+data.id);
		//console.log(data.hallName);
		$('#tableProjection').empty();
		newRow="<div>" +
				"<button type='button' class='btn btn-info hallBtn' id='projection_"+data.id+"' data-toggle='modal' data-target='#modalHall'>" +
				"Odaberi sediste</button><br>" +
				"<button type='button' >" +
				"Pozovi prijatelje</button>"+
				
				"</div>";
			
			
		$('#tableHall').append(newRow);
		}
		
	});

});

$(document).on("click",".hallBtn",function(event){
	projId = $(this).attr('id').split('_')[1];
	console.log("Stisnuli hall BTN PROJID JE"+projId);
	//ajax za sedista za datu sali koja su zauzeta i slobodna
	
	$.ajax({
		url:"/halls/inProjection/"+projId
	}).then(function (data){
		console.log(data);
		console.log("cinemaId"+data.cinemaId);
		console.log("hallId"+data.id)
		//projid saljem
		getHallProjConfUser(projId)
			//hall = "<div id='hallDiv_" + data.id + "' class='container-fluid col-xs-12 hall'>" + "<h4>" + data.hallName + "</h4>" + generateHallConf(1) + "" +
			//		"</div>"
		
		//$('#showSeats').append(hall);
		
	});
	
});

$(document).on("click","#rezervisiKartu",function(event){
	console.log("pris=tisnuto dugme rezervisi kartu");
	projectionId=$('input.hiddenfieldclass').val();
	//var hidden=$('input.hiddenfieldclassSalaId').val();
	console.log("hidden projection id"+projectionId);
	
	

	checkedBoxes = $('.isChecked:checkbox:checked')
	
//	za svako sediste napraviti po kartu
	if(checkedBoxes.length==0){
		alert("Nema slobodnih mesta pronadji drugi bioskop");
		$('#modalHall').modal('hide');
		
		 $('#tabs a[href="#bioskopi"]').tab('show');
		//vrati ponovo na bioskope
		//reserveDiv empty
		 $('#tableHall').empty();
			
	}
	
	for(i = 0; i < checkedBoxes.length; i++){
		seatId = checkedBoxes[i].id.split('_')[1]
		console.log("hidden projection id"+seatId );
		formData = JSON.stringify({
			projectionId: projectionId,
			seatId: seatId
		});
		
		$.ajax({
			url: "http://localhost:8080/tickets/createQuickUser",
			type: "POST",
			contentType: "application/json",
			dataType: "json",
			data: formData,
			success: function(data){
				
				
				$('#modalHall').modal('hide');
				alert("Uspesno ste rezervisali kartu")
				//getQuicks(data.projectionMovieCinemaId)
				console.log("sya je u data"+data)
				
			}
		});
		
	}
});

	/*salaId = $(this).attr('id').split('_')[1];
	
	console.log("SALA ID"+salaId);
	var time=$('#time').text();
	console.log(time);
	var date=$('#dateStart').text();
	console.log(date);
	//kreirati datum iz table
	var times =time.split(':');
	console.log("times[0]"+times[0]);
	console.log("times[1]"+times[1]-30);
	
	var parts =date.split('/');
	console.log("parts[0]"+parts[0]);
	console.log("parts[1]"+parts[1]);
	console.log("parts[2]"+parts[2]);
	var mydate = new Date(parts[2], parts[1]-1, parts[0],times[0],times[1],'00');	
	var d=formatDate(mydate);*/
	
	// var myDate = new Date().toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");
	 //console.log("novo"+myDate)
	// var myDateFront=  new Date(parts[2], parts[1]-1, parts[0],times[0],times[1]-30,'00').toTimeString().replace(/.*(\d{2}:\d{2}:\d{2}).*/, "$1");;
	// console.log("novo myDateFron "+myDateFront);
	// console.log("format"+d);
	//trenutni datum + 4h

	/*var today = new Date().toISOString().split('T')[0];
	
	console.log(today);

		$.ajax({
			url: "halls/hall/" + salaId
		}).then(function(data){
			//proveri pre submita vreme 
			if(d==today){
				console.log("isti je datum");
				console.log("isti je datum d" +d);
				console.log("isti je datum today"+ today);
				if(myDate>myDateFront){
					console.log("oooooooooooooooooooooooooooooooo");
					alert("Ostalo je manje 30 minuta do projekcije ne mozete rezervisati");
				}
			}
			$('#tableProjection').empty()
			
			console.log(data);
			console.log(data.id);
		} );
	checkTime();
	
	
	
});*/
function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function loadUpcomingReservations(){
	console.log("LoadUppcoming");
	
	$.ajax({
		url: "/tickets/getUppcomming"
	}).then(function(data){
		console.log("broj karata"+data.length);
	    displayReservations(data);
	});
	
}

function displayReservations(reservations){
	 if (reservations.length === 0){
	        $(".tableUpcoming").append("<label>There are no upcoming reservations.</label>");
	 }else{
		 console.log(reservations);
		 console.log(reservations.movieName)
		for(i=0;i<reservations.length;i++){
			console.log("reservation data"+reservations[i]);
			console.log("Reservation id pprojekcije" +reservations[i].id )
			console.log("Reservation id filma" +reservations[i].movieId )
			console.log("Reservation naziv filma" +reservations[i].movie )
			//console.log("Reservation naziv filma" +reservations.movie )
		}
		  $(".tableUpcoming").append("<label>Treba ih prikazati</label>");
	 }
	
}
