$(document).ready(function(){
		
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
								"<p class='name' style='overflow: hidden;'></p>" +
								"<p class='surname' style='overflow: hidden;' ></p>" +
								"<i class='glyphicon glyphicon-envelope'></i>" +
								"<p class='email' style='overflow: hidden;display:inline;'></p><br>" +
								"<i class='glyphicon glyphicon-phone-alt'></i>" +
								"<p class='phoneNumber' style='overflow: hidden;display:inline;'></p><br>" +
								"<i class='glyphicon glyphicon-map-marker'></i>" +
								"<p class='city' style='overflow: hidden;display:inline;'></p><br>" +
							"</blockquote>" +
						"</div>"
							
			$('#profileTab1').append(profileData);
			
			  $(".name").html(" " + data.name);
			  $(".surname").html(" " + data.surname);
			  $(".email").html(" " + data.email);
			  $(".phoneNumber").html(" " + data.phoneNumber);
			  $(".city").html(" " + data.city);
			  
			  $(".nameEdit").val(data.name);
			  $(".surnameEdit").val(data.surname);
			  $(".passwordEdit").val(data.password);
			  $(".townEdit").val(data.city);
			  $(".phoneNumber").val(data.phoneNumber);
			  
		},
		error:function(error){
			
		}
		
	});
		
	
})