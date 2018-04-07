$(document).ready(function(){
	
	$("#cinemaButton").click(function(){
		
		$(".buttonsHolder").fadeOut(1000, function(){
			$.ajax({
				url : "http://localhost:8080/cinemas/getCinemas"
			}).then(
					function(data) {
						
						$(".cinemaHolder").empty()
						
						for(i = 0; i < data.length; i++){
							
							prikaz = "<div class='cinemaDiv'>" +
									"<h3 class='cinemaName'>" + data[i].name + "</h3>" +
									"<p><label>Adresa: </label>" + data[i].address + "</p>" +
									"<p><label>Opis: </label>" + data[i].description + "</p>" +
									"<p><label>Prosecna ocena: </label>" + data[i].avgRating + "</p>" +
									"</div>"
								
							
							$(".cinemaHolder").append(prikaz)
						}
						

						$("#goBack").fadeIn(1000, function(){
							$(".cinemaHolder").fadeIn(1000)
						})
						
						
					});
		});
		
		
		
	});
	
	$("#goBack").click(function(){
		
		$(".cinemaHolder").fadeOut(1000, function(){
			$(".buttonsHolder").fadeIn(1000)
		})
		
		$("#goBack").fadeOut()
		
	});
	
});