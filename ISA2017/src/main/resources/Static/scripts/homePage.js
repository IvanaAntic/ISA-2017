$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	
	$("#izmeniDa").click(function(){
		console.log("desilo se");
		
		
	});
	
	
	$("#navbar-right").on('click', function (e) {
	    e.preventDefault();
	    $.ajax({
	    	type:'GET',
	    	contentType: "application/json",
			datatype: 'json',
			url:"http://localhost:8080/user/loggoutUser",
			 success: function(){
				 sessionStorage.removeItem('logged');
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
			console.log("d");
			  $(".name").html(" " + data.name);
			  $(".surname").html(" " + data.surname);
			  $(".email").html(" " + data.email);
			  $(".phoneNumber").html(" " + data.phoneNumber);
			  $(".city").html(" " + data.city)
		},
		error:function(error){
			console.log("e"+error);
		}
		
	})
}

