$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	loadFriendRequests();
	
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
						+"<td><button name='accept' class='btn btn-primary' style='width:80px;' id=" + data[i].id + ">Accept</button></td>"
						+"<td><button name='delete' class='btn btn-danger' style='width:80px;' id=" + data[i].id+ ">Reject</button></td>>"
						+"</tr>";
					$(".reqTable").append(newRow);
				}
			}
	});
}

$(document).on("click",".btn-primary",function(event){
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
    	}
	});
	
});
