var urlBase = "http://localhost:8080/";

function getUser(){
	
}

function isFanZoneAdmin(){
	var loggedUserId;
	$.ajax({
		method : 'GET',
		url : urlBase + "user/displayUser",
		success : function(data){
			if (data.role!= "FANZONEADMIN") {
				alert("Nemate prava pristupa!");
				window.location.href='index.html';
			}else{
				$("#editAdminForm [name='editAdminId']").val(data.id);
				$("#editAdminForm [name='adminNameInput']").val(data.name);
				$("#editAdminForm [name='adminSurnameInput']").val(data.surname);
				$("#editAdminForm [name='adminCityInput']").val(data.city);
				$("#editAdminForm [name='adminPhoneInput']").val(data.phoneNumber);
				$("#editAdminForm [name='adminEmailInput']").val(data.email);
				loggedUserId = data.id;
				
			}
				
		},
		error : function(xhr, status, error) {
			  var err = eval("(" + xhr.responseText + ")");
			  alert(err.error + " Status: "+err.status+"\nMorate biti ulogovani!");
			  window.location.href='index.html';
			}
	});
	return loggedUserId;
}
/*function isFanZoneAdmin(){
	loggedUserId = sessionStorage.loggedId;
	$.ajax({
		method : 'GET',
		url : urlBase + "user/info/"+loggedUserId,
		success : function(data){
			if (data.role!= "FANZONEADMIN") {
				alert("Nemate prava pristupa!");
				window.location.href='index.html';
			}else{
				$("#editAdminForm [name='id']").val(data.id);
				$("#editAdminForm [name='adminNameInput']").val(data.name);
				$("#editAdminForm [name='adminSurnameInput']").val(data.surname);
				$("#editAdminForm [name='adminCityInput']").val(data.city);
				$("#editAdminForm [name='adminPhoneInput']").val(data.phoneNumber);
				$("#editAdminForm [name='adminEmailInput']").val(data.email);
			}
				
		},
		error : function(error){
			window.location.href='index.html';
			alert(error.message);
			
		}
	});
}*/
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
	
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem/getAll",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				
					appendUserItem(data[i]);
			}
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
function approveUserItem(event){
	var loggedUserId = sessionStorage.loggedId;
	$.ajax({
		method : 'POST',
		url : urlBase + "FanZone/approve/"+event.id+"/"+loggedUserId,
		success : function(data){
			$("#userItemsNotApproved").find("#"+data.id+"").remove();
			appendUserItem(data);
			console.log(data)
		},
		error : function(error){
			
			
		}
	});
}
function disapproveUserItem(event){
	var loggedUserId = sessionStorage.loggedId;
	$.ajax({
		method : 'POST',
		url : urlBase + "FanZone/disapprove/"+event.id+"/"+loggedUserId,
		success : function(data){
			$("#userItemsApproved").find("#"+data.id+"").remove();
			appendUserItem(data);
			console.log(data)
		},
		error : function(error){
			
			
		}
	});
}
function removeUserItem(event){
	console.log(event.id);
	var confirmed = confirm("Da li ste sigurni da zelite da obrišete?");
	if(confirmed){
		$.ajax({
			method : 'DELETE',
			url : urlBase + "FanZone/deleteUserItem/"+event.id,
			
			success : function(data){
				console.log("Obrisan "+data);
				alert("Obrisali ste " + data.name);
				if (data.approved) {
					$("#userItemsApproved").find("#"+data.id+"").remove();
				} else {
					$("#userItemsNotApproved").find("#"+data.id+"").remove();
				}
			},
			error: function(error){
				alert("Greska");
			}
		});
		
	}
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
        id:$(document).find("#inputForm [name='itemId']").val(),
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
			$("#inputForm [name='itemId']").val(data.id);
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
	var postedBy = sessionStorage.loggedId;;
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
//Prikazuje samo one elementre koji se poklapaju sa pretragom
function searchItems() {
    
	var input,divs, filter, activeTab, i;
	activeTab = $("ul#tabs li.active").text();
	if (activeTab == "Moji proizvodi") 
		divs = $("#adminItemsAll").children();
	
	if(activeTab == "Oglasi na cekanju")
		divs = $("#userItemsNotApproved").children();
	
	if(activeTab == "Odobreni oglasi")
		divs = $("#userItemsApproved").children();
			
	
    input = document.getElementById('searchInput');
    filter = input.value.toUpperCase();
    
    for (i = 0; i < divs.length; i++) {
    	name = divs.eq(i).find("h3").text();
        if (name.toUpperCase().indexOf(filter) > -1) {
        	divs.eq(i).show();
        } else {
        	divs.eq(i).hide();
        }
    }
}
function appendUserItem(data){
	 
	newUserItem =		"<div id=\""+data.id+"\" class=\"card mt-4\">"
					+	"<div class=\"card-body\">"
					+"		<div class=\"col-lg-6\">"
					+"			<img class=\"card-img img-fluid mt-3\" src=\"http://via.placeholder.com/250x250\" >"
					+"		</div>"                 
					+"		<div class=\"col-lg-6\">"
				    +  	"<h3 class=\"card-title\" id=\"userItemName\">"+data.name+"</h3>"
					+      "<p class=\"card-text\" id=\"userItemDescription\">"+data.description+"</p>"
					+     	"<table class=\"table\">"
				    +	   		"<tbody>"
					+	     	"<tr>"
					+	        	"<td>Prodavac:</td>"
					+	        	"<td id=\"postedByName\">"+data.postedByName+"</td>"
					+				"<td id=\"postedById\" style=\"display:none;\">"+data.postedById+"</td>"
					+	      	"</tr>"
					+	      	"<tr>"
					+	        "<td>Cena:</td>"
					+	        "<td id=\"price\">"+data.startPrice+"</td>"
					+	      "</tr>"
					+	      "<tr>"
					+	        "<td>Aktuelna ponuda:</td>"
					+	        "<td id=\"currentBid\">"+data.currentPrice+"</td>"
					+	      "</tr>"
					+	      "<tr>"
					+	        "<td >Zavrsetak licitacije:</td>"
					+	        "<td id=\"endTime\">"+data.endDate+"</td>"
					+	      "</tr>"
					+	      "<tr>"
					+	        "<td >Status:</td>"
					+	        "<td id=\"status\">"+data.status+"</td>"
					+	      "</tr>"
					+	    "</tbody>"
					+	  "</table>"     
					+      "<div id=\"userItemButtons"+data.id+"\"  style=\"float : right\">"   
					+        "<a id=\""+data.id+"\" class=\"btn btn-danger m-3\" onclick=\"removeUserItem(this)\">Ukloni</a>"
					+      "</div>"
					+    "</div>"
					+    "</div>"
					+  "</div>";
		if (data.approved && (data.approvedById == sessionStorage.loggedId)) {
			
			$("#userItemsApproved").append(newUserItem);
			$("#userItemButtons"+data.id).append("<a id=\""+data.id+"\" class=\"btn btn-warning\" onclick=\"disapproveUserItem(this)\" >Zabrani</a>");
			
		} else {
			
			$("#userItemsNotApproved").append(newUserItem);
			$("#userItemButtons"+data.id).append("<a id=\""+data.id+"\" class=\"btn btn-success\" onclick=\"approveUserItem(this)\" >Odobri</a>");
		}		
		
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
			  +  "<div class=\"card-body\">"
			  +"		<div class=\"col-lg-6\">"
			  +"			<img class=\"card-img img-fluid mt-3\" src=\"http://via.placeholder.com/250x250\" >"
			  +"		</div>"
			  +" 		<div class=\"col-lg-6\">"
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
			  +     "<div style=\"float : right\" >"
			  +      "<a  id=\""+data.id+"\"class=\"btn btn-danger mb-3\" onclick=\"removeAdminItem(this)\" >Ukloni</a>"
			  +      " <a  id=\""+data.id+"\"class=\"btn btn-warning mb-3\" onclick=\"editAdminItemClicked(this)\" >Izmeni</a>"
			  +     "</div>"
			  +  "</div>"
			  +  "</div>"
			  + "</div>"			
			  +"</div>";
	$("#adminItemsAll").append(newItem);
}
