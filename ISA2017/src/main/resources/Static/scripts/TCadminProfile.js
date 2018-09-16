$(document).ready(function(){
	
	$("#getProfileData").click(function(){
		
		$("#profileTab1").empty()
		$("#profileTab2").empty()
		
		$("#goBackPlay").fadeOut()
		$("#goBackMovies").fadeOut()
		
		$(".buttonsHolder").fadeOut();
		$(".cinemaHolder").fadeOut();
		$(".theatreHolder").fadeOut();
		$(".reportHolder").fadeOut();
		$(".profileHolder").delay(500).fadeIn();
		$("#goHome").delay(500).fadeIn();
		
		loadAdminUser();

	});
	
	$("#changePassButton").click(function(){
		
		
		
	});
		
	
});

$(document).on('click', '#editAdminProfile', function(){
	
	formData= JSON.stringify({
		name: $(".nameProfileEdit").val(),
		surname: $(".surnameProfileEdit").val(),
		city: $(".townProfileEdit").val(),
		phoneNumber: $(".phoneNumberProfileEdit").val(),
          });
	
	$.ajax({
		type:"POST",
		url:"http://localhost:8080/user/editUser",
		contentType: "application/json",
		datatype: "json",
		data:formData,
		success:function(data){
			 alert("Profile data changed successfully! ");
			 loadAdminUser();
		}
		
	});
	
});

function loadAdminUser(){
	
	$("#profileTab1").empty()
	$("#profileTab2").empty()
	
	$.ajax({
		
		type:'GET',
		url:"http://localhost:8080/user/displayUser",
		contentType: "application/json",
		datatype: 'json',
		success:function(data){
			
			profileData = "" +
						"<div class='userData'>" +
							"<img src='images/slika.gif' alt='Userphoto' style='float: left;padding: 0 20px 20px 0;border : 1px;width: 200px;height: 200px;' />" +
							"<blockquote>" +
								"<p class='nameProfile' style='overflow: hidden;'></p>" +
								"<p class='surnameProfile' style='overflow: hidden;' ></p>" +
								"<i class='glyphicon glyphicon-envelope'></i>" +
								"<p class='emailProfile' style='overflow: hidden;display:inline;'></p><br>" +
								"<i class='glyphicon glyphicon-phone-alt'></i>" +
								"<p class='phoneNumberProfile' style='overflow: hidden;display:inline;'></p><br>" +
								"<i class='glyphicon glyphicon-map-marker'></i>" +
								"<p class='cityProfile' style='overflow: hidden;display:inline;'></p><br>" +
							"</blockquote>" +
						"</div>"
							
			$('#profileTab1').append(profileData);
			
			profileEdit = "" +
			           "<h1>Unesite podatke za izmenu:</h1>"
			            +"<form id='inputForm' class='izmena' role='form'>"
				         +   "<p>"
				          +  	"<label for='name'>Ime:</label>"
				           + 	"<input class='nameProfileEdit' type='text' name='nameEdit' id='name'></input><br>"
				            +"</p>"
				            +"<p>"
				            +	"<label for='surname'>Prezime:</label>"
				            +	"<input class='surnameProfileEdit' type='text' name='nameEdit' id='name'></input><br>"
				            +"</p>"
				            +"<p>"
			            	+	"<label for='town'>Grad:</label>"
			            	+	"<input class='townProfileEdit' type='text' name='nameEdit' id='name'></input><br>"
							+"</p>"
							+"<p>"
							+	"<label for='phoneNumber'>Broj telefona:</label>"
							+	"<input class='phoneNumberProfileEdit' type='text' name='nameEdit' id='name'></input><br>"
							+"</p>"
				            +"<p>"
			            	+	"<input class='btn btn-primary' type='button' name='nameEdit' id='editAdminProfile' value='Izmeni'></input><br>"
							+"</p>"
			            +"</form>"
			            
			  $('#profileTab2').append(profileEdit);
			
			  $(".nameProfile").html(" " + data.name);
			  $(".surnameProfile").html(" " + data.surname);
			  $(".emailProfile").html(" " + data.email);
			  $(".phoneNumberProfile").html(" " + data.phoneNumber);
			  $(".cityProfile").html(" " + data.city);
			  
			  $(".nameProfileEdit").val(data.name);
			  $(".surnameProfileEdit").val(data.surname);
			  //$(".passwordEdit").val(data.password);
			  $(".townProfileEdit").val(data.city);
			  $(".phoneNumberProfileEdit").val(data.phoneNumber);
			  
		},
		error:function(error){
			
		}
		
	});
}