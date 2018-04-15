$(document).ready(function(event){
	console.log("ovde")
	loadUser();
	
	$("#izmeniDa").click(function(){
		console.log("desilo se");
		
		
	});
	
	
	
});


function loadUser(){
	$.ajax({
		
		type:'GET',
		url:"http://localhost:8080/user/displayUser",
		contentType: "application/json",
		datatype: 'json',
		sucsses:function(){
			console.log("d");
		},
		error:function(error){
			console.log("e"+error);
		}
		
	})
	
	
}