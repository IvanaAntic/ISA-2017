var urlBase = "http://localhost:8080/"
	

function getPlaces(){
	$("#cinemaOption").children().remove();
	$("#theatreOption").children().remove();
	$.ajax({
		method : 'GET',
		url : urlBase + "cinemas/getCinemas",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				newOption = "<option value=\""+data[i].id+"\" >"+ data[i].name+"</option>";
				$(".cinemaOption").append(newOption);
			}
			
		},
		error: function(error){
			console.log("Neki error");
		}
	});
	$.ajax({
		method : 'GET',
		url : urlBase + "theatres/getTheatres",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				newOption = "<option value=\""+data[i].id+"\" >"+ data[i].name+"</option>";
				$(".theatreOption").append(newOption);
			}
			
		},
		error: function(error){
			console.log("Neki error");
		}
	});
}
function getAdminItems(){
	$.ajax({
		method : 'GET',
		url : urlBase + "FanZone/adminItems",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				if (data[i].postedById == sessionStorage.loggedId) {
					appendItem(data[i]);
				}
				
			}
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
	
}
function getUserItems(){
	
}
function removeAdminItem(event){
	console.log(event.id);
	var confirmed = confirm("Da li ste sigurni da zelite da obrišete?");
	if(confirmed){
		$.ajax({
			method : 'DELETE',
			url : urlBase + "FanZone/deleteAdminItem/"+event.id,
			
			success : function(data){
				console.log("Obrisan "+data);
				alert("Obrisali ste " + data.name);
				$("#adminItemsAll").find("#"+data.id+"").remove();
				$('#inputModal').modal('toggle');
			},
			error: function(error){
				alert("Greska");
			}
		});
		
	}
	
}
function sendEditAdminItem(){
	console.log("proba");
	var theatrePlace = $(document).find(".theatreOption option:selected").val();
	var cinemaPlace = $(document).find(".cinemaOption option:selected").val();
	formData = JSON.stringify({
        id:$(document).find("#inputForm [name='id']").val(),
		name:$(document).find("#inputForm [name='nameInput']").val(),
        description:$("#inputForm [name='descriptionInput']").val(),
        price:$("#inputForm [name='priceInput']").val(),
        theatreId:theatrePlace,
        cinemaId:cinemaPlace
    });	
	
	$.ajax({
		method : 'PUT',
		url : urlBase + "FanZone/editAdminItem",
		data: formData,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			//vrati onclik na staro
			$('#addNewItem').attr('onclick','addNewAdminItem()');
			if (data.theatreName) {
				placeName = data.theatreName;
				placeId = data.theatreId;
			} else {
				placeName = data.cinemaName;
				placeId = data.cinemaId;
			}
			edited = $("#adminItemsAll").find("#"+data.id+"");
			edited.find("#adminItemName").html(data.name);
			edited.find("#adminItemDescription").html(data.description);
			edited.find(".placeName").html(placeName);
			edited.find(".placeId").html(placeId);
			edited.find(".price").html(data.placeName);
		},	
		error: function(error){
			alert("Doslo je do greske.")
		}
	});
}
function editAdminItemClicked(event){
	var forEdit = $("#adminItemsAll").find("#"+event.id+"");
	name = forEdit.find("#adminItemName").html();
	description = forEdit.find("#adminItemDescription").html();
	price = forEdit.find(".price").html();
	placeId = forEdit.find(".placeId").html();

	
	
	$.ajax({
		method : 'GET',
		url : urlBase + "FanZone/adminItem/"+event.id,
		success : function(data){
			console.log(data);
			$("#inputForm [name='id']").val(data.id);
			$("#inputForm [name='nameInput']").val(data.name);
			$("#inputForm [name='descriptionInput']").val(data.description);
			$("#inputForm [name='priceInput']").val(data.price);
			
			if (data.theatreName) {
				placeName = data.theatreName;
				placeId = data.theatreId;
				$(".theatreOption option[value="+placeId+"]").attr('selected', 'selected');
			} else {
				placeName = data.cinemaName;
				placeId = data.cinemaId;
				$(".cinemaOption option[value="+placeId+"]").attr('selected', 'selected');
			}
			$('#addNewItem').attr('onclick','sendEditAdminItem()');
			
			
		},
		error: function(error){
			alert("Greska");
		}
		
	});
	$('#newItemModal').modal('toggle');
}
function addNewAdminItem(){
	//$('#newItemModal').modal('toggle');
	$('#addNewItem').attr('onclick','addNewAdminItem()');
	var theatrePlace = $(document).find(".theatreOption option:selected").val();
	var cinemaPlace = $(document).find(".cinemaOption option:selected").val();
	//Moramo iz sesije znati ko je trenutno ulogovan
	var postedBy = 1
	var formData;
	
	
//pripremamo JSON koji cemo poslati
	formData = JSON.stringify({
                name:$(document).find("#inputForm [name='nameInput']").val(),
                description:$("#inputForm [name='descriptionInput']").val(),
                price:$("#inputForm [name='priceInput']").val(),
                theatreId:theatrePlace,
                cinemaId:cinemaPlace,
                postedById:postedBy
            });	
	
	
	$.ajax({
		method : 'POST',
		url : urlBase + "FanZone/addNewItem",
		data: formData,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			appendItem(data);
			$('#newItemModal').addClass("modal fade");
		},
		error: function(error){
			alert("Greska");
		}
	});
	
}
	
function appendItem(data){
	if (data.theatreName) {
		placeName = data.theatreName;
		placeId = data.theatreId;
	} else {
		placeName = data.cinemaName;
		placeId = data.cinemaId;
	}
	if (data.isReserved) {
		status = "Rezervisan";
	}else{
		status = "Slobodan";
	}
		
	newItem = "<div id=\"" + data.id +  "\" class=\"card mt-4\">"
			  + " <img class=\"card-img-top img-fluid\" src=\"http://placehold.it/150x100\" alt=\"\">"
			  +  "<div class=\"card-body\">"
			  +  "  <h3 class=\"card-title\" id=\"adminItemName\">" + data.name + "</h3>"
			  +    "<p class=\"card-text\" id=\"adminItemDescription\">" + data.description + "</p>"
			  +    "<table class=\"table\">"
			  +		"<tbody>"
			  +			"<tr>"
			  +				"<td>Mesto:</td>"
			  +        		"<td class=\"placeName\">"+placeName+"</td>"
			  +				"<td class=\"placeId\" style=\"display:none;\">"+placeId+"</td>"
			  +      	"</tr>"
			  +	      	"<tr>"
			  +	        	"<td>Cena:</td>"
			  +	       	 	"<td class=\"price\">"+ data.price + "</td>"
			  +	      	"</tr>"
			  +	      	"<tr>"
			  +	        	"<td>Status:</td>"
			  +	        	"<td class=\"status\">"+ status + "</td>"
			  +	      	"</tr>"
			  +	    	"</tbody>"
			  +	  	"</table>"
			  +     "<div style=\"float : right\">"
			  +      "<a href=\"#\" id=\""+data.id+"\"class=\"btn btn-danger\" onclick=\"removeAdminItem(this)\" >Ukloni</a>"
			  +      " <a href=\"#\" id=\""+data.id+"\"class=\"btn btn-warning\" onclick=\"editAdminItemClicked(this)\" >Izmeni</a>"
			  +     "</div>"
			  +  "</div>"
			  + "</div>"			
			  +"</div>";
	$("#adminItemsAll").append(newItem);
}
