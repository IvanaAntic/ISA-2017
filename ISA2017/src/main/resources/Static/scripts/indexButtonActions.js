$(document).ready(function(){
	
	$("#cinemaButton").click(function(){
		
		$(".mainContentHolder").hide(2000);
		
		$.ajax({
			url : "http://localhost:8080/cinemas/getCinemas"
		}).then(
				function(data) {
					
					prikaz = "<p>" + data[1].name + "</p>"
					
					$(".mainContentHolder").empty()
					.append(prikaz)
					.append("<br><button class='goBack'>Nazad</button>")
					.show(2000)
					
				});
		
	});
	
	$(".goBack").click(function(){
		
		
		
	});
	
});