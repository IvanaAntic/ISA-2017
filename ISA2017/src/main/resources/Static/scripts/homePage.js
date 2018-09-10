$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	loadFriendRequests();
	loadVisitHistory();
	loadFriendAccepted();
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
    			//loadFriendRequests();
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
    			//loadFriendRequests();
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
    		//loadFriendRequests();
    	}
    });
});

