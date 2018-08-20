$(document).ready(function(){
	
	$("#getReportsButton").click(function(){
		
		$("#tab1").empty()
		$("#tab2").empty()
		$("#tab3").empty()
		$("#tab4").empty()
		
		$("#goBackPlay").fadeOut()
		$("#goBackMovies").fadeOut()
		
		$(".buttonsHolder").fadeOut();
		$(".cinemaHolder").fadeOut();
		$(".theatreHolder").fadeOut();
		$(".profileHolder").fadeOut();
		$(".reportHolder").delay(500).fadeIn();
		$("#goHome").delay(500).fadeIn();
		
		$.ajax({
			url : "http://localhost:8080/cinemas/getTCadminCinemas"
		}).then(function(data){
			
			cinemas = "<table class='table table-bordered'>" +
					"<tr>" +
					"<th>Bioskop</th>" +
					"<th>Prosecna ocena</th>" +
					"</tr>"
					
			for(i = 0; i < data.length; i++){
				
				cinemas+="<tr>" +
						"<td>" + data[i].name + "</td>" +
						"<td>" + data[i].avgRating + "</td>" +
						"</tr>"
				
			}
			
			cinemas+="</table>"
				
			$("#tab1").append(cinemas)
			
		});
		
		$.ajax({
			url : "http://localhost:8080/cinemas/getTCadminMovies"
		}).then(function(data){
			
			movies = "<table class='table table-bordered'>" +
					"<tr>" +
					"<th>Film</th>" +
					"<th>Prosecna ocena</th>" +
					"</tr>"
					
			for(i = 0; i < data.length; i++){
				
				movies+="<tr>" +
						"<td>" + data[i].name + "</td>" +
						"<td>" + data[i].rating + "</td>" +
						"</tr>"
				
			}
			
			movies+="</table>"
				
			$("#tab2").append(movies)
			
		});
		
	});
	
	$("#changePassButton").click(function(){
		
		formData = JSON.stringify({
			old: $("#oldPasswordProfile").val(),
			newPass: $("#newPasswordProfile").val(),
			newConfirmed: $("#newPasswordProfileConfirm").val()
		 });
		
		$.ajax({
		
			url: "/user/changePassword",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data) {
				alert("Uspesno promenjena lozinka!");
			},
			error : function(error) {
				alert("Neispravno ");
			}
		});
		
	});
	
});