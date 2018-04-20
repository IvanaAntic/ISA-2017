$(document).ready(function(event){
	console.log("ovde")
	loadUser();

	
	$("#izmeni").click(function(){
		console.log("desilo se");
		userEdit();
		
		
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



