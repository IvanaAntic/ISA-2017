var urlBase = "http://localhost:8080/";

$(document).ready(function() {
    $(function () {
        $('#dateTimePicker').datetimepicker({
        	format : 'YYYY-MM-DD HH:mm',
        	minDate: moment()
        });
        
    });
    
});
function loggedUser(){

	$.ajax({
		method : 'GET',
		url : urlBase + "user/displayUser",
		success : function(data){

			sessionStorage.setItem('loggedId',data.id);
	
		},
		error : function(xhr, status, error) {
			  var err = eval("(" + xhr.responseText + ")");
			  alert(err.error + " Status: "+err.status+"\nMorate biti ulogovani!");
			  window.location.href='index.html';
			}
	});
}
function addNewUserItem(){
	var postedBy = sessionStorage.loggedId;

	formData = 	JSON.stringify({
        		name:$(document).find("#newItemModal [name='itemNameInput']").val(),
        		description:$("#newItemModal [name='itemDescriptionInput']").val(),
        		startPrice:$("#newItemModal [name='itemPriceInput']").val(),
        		currentPrice:$("#newItemModal [name='itemPriceInput']").val(),
        		endDate:$("#dateTimePicker").find("input").val(),
        		postedById:postedBy
    });	
	$.ajax({
		method : 'POST',
		url : urlBase + "userItem/addUserItem",
		data: formData,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			$('#newItemModal').modal('toggle');
		},
		error: function(error){
			alert("Greska");
		}
	});
}
function getUserItems(){
	var user = sessionStorage.loggedId;
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem/getApproved",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			if (data.length == 0) {
				$("#userItems").append("<h4>Nema proizvoda.</h4>");
			} else {
				for (i = 0; i < data.length; i++) {
					
					if (data[i].postedById != user) 
						appendUserItem(data[i]);				
					
				}
			}
			
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
function getMyOrders(){
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem/myOrders",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			if (data.length == 0) {
				$("#myOrders").append("<h4>Nema proizvoda.</h4>");
			} else {
				for (i = 0; i < data.length; i++) {
					
					appendMyOrders(data[i]);				
					
				}
			}
			
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
function getMyUserItems(){
	var user = sessionStorage.loggedId;
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem/postedBy/"+user,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			if (data.length == 0) {
				$("#myUserItem").append("<h4>Nema proizvoda.</h4>");
			} else {
				for (i = 0; i < data.length; i++) {		
					appendMyUserItem(data[i]);			
				}
			}		
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
function getMyItemBids(event){
	$("#myUserItemBidsTable tr").remove();
	$("#bestBid tr").remove();
	var userItem = event.id;
	var itemBids, bidRow, tr, max;
	var userId = sessionStorage.loggedId;
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem//userItem/"+userItem,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			bids = data.bids;
			maxBid = bids[0];
			if (bids.length > 0) {
				
			
				for (i = 0; i < bids.length; i++) {
					if (bids[i].price > maxBid.price) {
						maxBid = bids[i];
					}
				}
				for(i = 0; i < bids.length; i++) {
					bidRow = "<tr>"						
							+"	<td id=\"myItemBidderName\">"+bids[i].buyerName+"</td>"
							+"	<td id=\"myItemBidPrice\">"+bids[i].price+"</td>"
							+"	<td id=\"myItemBidId\" style=\"display:none;\">"+bids[i].id+"</td>"
							+"	<td id=\"myItemBidderId\" style=\"display:none;\">"+bids[i].buyerId+"</td>"
							+"</tr>";
					$("#myUserItemBidsTable").append(bidRow);
				}
				bestBidRow = "<tr>"	
							+"	<td id=\"myItemBestBidderName\">"+maxBid.buyerName+"</td>"
							+"	<td id=\"myItemBestBidPrice\">"+maxBid.price+"</td>"
							+"	<td id=\"myItemBestBidId\" style=\"display:none;\">"+maxBid.id+"</td>"
							+"	<td id=\"myItemBestBidderId\" style=\"display:none;\">"+maxBid.buyerId+"</td>"
							+"	<td id=\"myItemId\" style=\"display:none;\">"+data.id+"</td>"
							+"</tr>";
				$("#bestBid").append(bestBidRow);
				$("#myUserItemBidsModal").modal('toggle')
			}else{
				alertModal("Nema ponuda.");
			}
				
		},
		error: function(error){
			console.log("Greska");
		}
	});
	
}
function getItemBids(event){
	$("#userItemBidsTable tr").remove();
	var userItem = event.id;
	var itemBids, bidRow, tr;
	var userId = sessionStorage.loggedId;
	$.ajax({
		method : 'GET',
		url : urlBase + "userItem/getApproved",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			j=0;
			for (i = 0; i < data.length; i++) {
				if(data[i].id == userItem)
					bids = data[i].bids;
			}
			for (i = 0; i < bids.length; i++) {
				if (bids[i].buyerId == userId) {
					tr = "<tr class=\"table-success\">";
				}else{
					tr = "<tr>";
				}
				bidRow = tr						
						+"	<td id=\"itemBidderName\">"+bids[i].buyerName+"</td>"
						+"	<td id=\"itemBidPrice\">"+bids[i].price+"</td>"
						+"	<td id=\"itemBidId\" style=\"display:none;\">"+bids[i].id+"</td>"
						+"	<td id=\"itemBidderId\" style=\"display:none;\">"+bids[i].buyerId+"</td>"
						+"</tr>";
				$("#userItemBidsTable").append(bidRow);
			}
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
	
}
function alertModal(alert){
	
	$("#alertModal").find("#alert").text(alert);
	$("#alertModal").modal('toggle');
}
function addNewBid(event){
	var itemId = event.id;
	var userId = sessionStorage.loggedId;
	var item = $('#userItems').find("#"+itemId+"");
	var newPrice = item.find("#newBidInput").val();
	//Datum mora biti u formatu "2018-04-18 10:43:57"
	var nowDate = moment().format("YYYY-MM-DD HH:mm");
	
	formData = JSON.stringify({
		date:nowDate,
		buyerId:userId,
		itemId:itemId,
		price:newPrice
	});
	
		$.ajax({
		method : 'POST',
		url : urlBase + "bid/addBid",
		data: formData,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			$("#userItems div").remove();
			getUserItems();
			
		},
		error: function(error){
			alertModal(error.responseJSON.message);
			$("#userItems div").remove();
			getUserItems();
		}
		});
	
}
function acceptBid(){
	
	var itemId = $('#bestBid').find('#myItemId').text();
	var item = $('#myItems').find("#"+itemId+"");
	var nowDate = moment().format("YYYY-MM-DD HH:mm");
	var bidId = $('#bestBid').find('#myItemBestBidId').text();
	var buyerId = $('#bestBid').find('#myItemBestBidderId').text();
	formData = JSON.stringify({
		date:nowDate,
		id:bidId,
		itemId:itemId,
		buyerId:buyerId
	});
		
		$.ajax({
			method : 'POST',
			url : urlBase + "bid/acceptBid",
			data: formData,
			contentType: "application/json",
			datatype: 'json',
			success : function(data){
				console.log(data);

				$("#myItems div").remove();
				getMyUserItems();
				$("#myUserItemBidsModal").modal('toggle');
			},
			error: function(error){
				alertModal(error.responseJSON.message);
				$("#myItems div").remove();
				getMyUserItems();
				$("#myUserItemBidsModal").modal('toggle');
			}
			});
	
	
}
function rejectBid(){
	var itemId = $('#bestBid').find('#myItemId').text();

		$.ajax({
			method : 'POST',
			url : urlBase + "bid/rejectBid"+itemId,
			data: formData,
			contentType: "application/json",
			datatype: 'json',
			success : function(data){
				console.log(data);
				$("#myItems div").remove();
				getMyUserItems();
				
				
			},
			error: function(error){
				alertModal(error.responseJSON.message);
				$("#myItems div").remove();
				getMyUserItems();
				
			}
			});
		$("#myUserItemBidsModal").modal('toggle');
	
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
				$("#myItems").find("#"+data.id+"").remove();
				
			},
			error: function(error){
				alert("Greska");
			}
		});
		
	}
}
function reservationClicked(event){
	var itemId = event.id;
	var item = $("#adminItems").find("#"+itemId+"")
	var modal = $("#acceptReservation");
	modal.find("#reserId").text(itemId);
	modal.find("#reserName").text(item.find("#adminItemName").text());
	modal.find("#reserPrice").text(item.find("#adminItemPrice").text());
	modal.find("#reserPlace").text(item.find("#adminItemPlace").text());
	
	$("#acceptReservation").modal('toggle');
	
}

function makeReservation(){
	var itemId = $("#acceptReservationTable").find("#reserId").text();
	$.ajax({
		method : 'GET',
		url : urlBase + "FanZone/reservation/"+itemId,
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			
			alertModal("Uspesno rezervisan: " + data.name);
			$("#adminItems").find("#"+data.id+"").remove()
		},
		error: function(error){
			console.log(error.status);
			alertModal(error.responseJSON.message);
			if (error.status == "500") {
				$("#adminItems").find("#"+itemId+"").remove()
			}
		}
	});
	
}
function getReservedByUser(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "FanZone/reservedItems",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				
				appendMyReservations(data[i]);				
				
			}
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
function getAdminItems(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "FanZone/adminItemsForSale",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			if (data.length == 0) {
				$("#adminItems").append("<h4>Nema proizvoda.</h4>");
			} else {
				for (i = 0; i < data.length; i++) {					
					appendAdminItem(data[i]);					
				}
			}
			
			
		},
		error: function(error){
			console.log("Greska");
		}
	});
}
//Prikazuje samo one elementre koji se poklapaju sa pretragom
function searchItems() {
    
	var input,divs, filter, activeTab, i;
	activeTab = $("ul#tabs li.active").text();
	if (activeTab == "Zvanicna prodavnica") {
		divs = $("#adminItems").children();
	} else {
		divs = $("#userItems").children();
	}
	
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
function appendMyOrders(data){
	
	var row = 	 "<tr>"
				+"	<td>"+data.id+"</td>"
				+"	<td>"+data.endDate+"</td>"
				+"	<td>"+data.name+"</td>"
				+"	<td>"+data.startPrice+"</td>"
				+"	<td>"+data.currentPrice+"</td>"
				+"	<td>"+data.postedByName+"</td>"
				+"	<td>"+data.status+"</td>"
				+"<tr>";
	$("#myOrdersTable").append(row);
}
function appendMyReservations(data){
	var placeName, placeId;
	if (data.theatreName) {
		placeName = data.theatreName;
		placeId = data.theatreId;
	} else {
		placeName = data.cinemaName;
		placeId = data.cinemaId;
	}
	
	var row = 	 "<tr>"
				+"	<td>"+data.id+"</td>"
				+"	<td>"+data.name+"</td>"
				+"	<td>"+data.price+"</td>"
				+"	<td>"+data.reservationDate+"</td>"
				+"	<td>"+placeName+"</td>"
				+"	<td>Zavrsena</td>"
				+"<tr>";
	$("#myReservationsTable").append(row);
	
}
function appendMyUserItem(data){
	
	var myUserItem = "<div id=\""+data.id+"\" class=\"card mt-4\">"
					+"<div class=\"card-body\">" 
					+"		<div class=\"col-lg-6\">"
					+"			<img class=\"card-img img-fluid mt-3\" src=\"http://via.placeholder.com/250x250\" >"
					+"		</div>"                 
					+"		<div class=\"col-lg-6\">"
					+"		<h3 id=\"myUserItemName\" class=\"card-title\">"+data.name+"</h3>"
					+"		<p class=\"card-text\">"+data.description+"</p>"
					+"	<table id=\"myUserItemInfo"+data.id+"\" class=\"table\">"
					+"	    <tbody>"
					+"		    <tr>"
					+"		        <td >Cena:</td>"
					+"		        <td id=\"myUserItemStartPrice\">"+data.startPrice+"</td>"
					+"		     </tr>"
					+"	      	 <tr>"
					+"	        	<td style=\"text-align: center;text-align: left;\">Aktuelna ponuda:</td>"
					+"	        	<td id=\"myItemCurrentBid\">"+data.currentPrice+"</td>"
					+"	      	 </tr>"
					+"	      	 <tr>"
					+"	        	<td >Zavrsetak licitacije:</td>"
					+"	        	<td id=\"myUserItemEndTime\">"+data.endDate+"</td>"
					+"	      	</tr>"
					+"	      	 <tr>"
					+"	        	<td >Status:</td>"
					+"	        	<td id=\"myUserItemStatus\">"+data.status+"</td>"
					+"	      	</tr>"
					+"	    </tbody>"
					+"	</table>"							
					+"   <div class=\"input-group mb-3\" >"
					+"      <span class=\"input-group-btn\">"
					+"     	<button id=\""+data.id+"\" class=\"btn btn-danger \" onclick=\"removeUserItem(this)\" type=\"button\">Izbrisi</button>"
					+"     	<button id=\""+data.id+"\" class=\"btn btn-primary \" onclick=\"editUserItem(this)\" type=\"button\">Izmeni</button>"
					+"		<button id=\""+data.id+"\" class=\"btn btn-primary ml-2 \" onclick=\"getMyItemBids(this)\" type=\"button\">Ponude ("+data.bids.length+")</button>"
					+"     </span>"
					+"    </div>"			       
					+"		</div>"
					+"	</div>"                  
					+"</div>";
					
	$("#myItems").append(myUserItem);
	if (data.buyerId != null) {
		buyerRow = "<tr>"
					+" 	<td >Kupac:</td>"
					+"	<td id=\"myUserItemBuyer\">"+data.buyerName+"</td>"
					+"</tr>;" 
	$("#myUserItemInfo"+data.id).append(buyerRow);				
	}
	
}
function appendUserItem(data){
	
	var newUserItem = "<div id=\""+data.id+"\" class=\"card mt-4\">"
					+"<div class=\"card-body\">" 
					+"		<div class=\"col-lg-6\">"
					+"			<img class=\"card-img img-fluid mt-3\" src=\"http://via.placeholder.com/250x250\" >"
					+"		</div>"                 
					+"		<div class=\"col-lg-6\">"
					+"		<h3 id=\"userItemName\" class=\"card-title\">"+data.name+"</h3>"
					+"		<p class=\"card-text\">"+data.description+"</p>"
					+"	<table class=\"table\">"
					+"	    <tbody>"
					+"		    <tr>"
					+"		        <td >Prodavac:</td>"
					+"		        <td id=\"userItemPostedBy\">"+data.postedByName+"</td>"
					+"		     </tr>"
					+"		    <tr>"
					+"		        <td >Cena:</td>"
					+"		        <td id=\"userItemStartPrice\">"+data.startPrice+"</td>"
					+"		     </tr>"
					+"	      	 <tr>"
					+"	        	<td style=\"text-align: center;text-align: left;\">Aktuelna ponuda:</td>"
					+"	        	<td id=\"currentBid\">"+data.currentPrice+"<button id=\""+data.id+"\"  onclick=\"getItemBids(this)\" href=\"#userItemBidsModal\" data-toggle=\"modal\" data-target=\"#userItemBidsModal\" type=\"button\" class=\"btn btn-sm\">Ponude("+data.bids.length+")</button></td>"
					+"	      	 </tr>"
					+"	      	 <tr>"
					+"	        	<td >Zavrsetak licitacije:</td>"
					+"	        	<td id=\"userItemEndTime\">"+data.endDate+"</td>"
					+"	      	</tr>"
					+"	    </tbody>"
					+"	</table>"							
					+"   <div id=\"addNewBidForm\" class=\"input-group mb-3\" >"
					+"     <input id=\"newBidInput\" type=\"number\" min=\""+data.currentPrice+"\" value=\""+data.currentPrice+"\" step=\"50\" max=\"500000\" class=\"form-control\" placeholder=\"Unesite ponudu..\">"
					+"      <span class=\"input-group-btn\">"
					+"     	<button id=\""+data.id+"\" class=\"btn btn-primary \" onclick=\"addNewBid(this)\" type=\"button\">Licitiraj</button>"
					+"     </span>"
					+"    </div>"			       
					+"		</div>"
					+"	</div>"                  
					+"</div>";
					
	$("#userItems").append(newUserItem);
	
}
function appendAdminItem(data){
	var placeName, placeId;
	if (data.theatreName) {
		placeName = data.theatreName;
		placeId = data.theatreId;
	} else {
		placeName = data.cinemaName;
		placeId = data.cinemaId;
	}
	
	var newAdminItem = 	 "<div id=\""+data.id+"\" class=\"card mt-4\">"
						+"<div class=\"card-body\"> "
						+"		<div class=\"col-lg-6\">"
						+"			<img id=\"adminItemImage\" class=\"card-img img-fluid\" src=\"http://via.placeholder.com/250x250\" >"
						+"	</div>"              
						+"		<div class=\"col-lg-6\">"
						+"		<h3 id=\"adminItemName\" class=\"card-title\">"+data.name+"</h3>"
						+"			<p id=\"adminItemDescription\" class=\"card-text\">"+data.description+"</p>"
						+"			<table class=\"table\">"
						+"			<tbody>"
						+"				<tr>"
						+"					<td>Mesto:</td>"
						+"					<td id=\"adminItemPlace\">"+placeName+"</td>"
						+"				</tr>"
						+"		      	<tr>"
						+"		        	<td >Cena:</td>"
						+"		        	<td id=\"adminItemPrice\">"+data.price+"</td>"
						+"		      	</tr>"
						+"	     	</tbody>"
						+"			</table>"
						+"			<a id=\""+data.id+"\"  class=\"btn btn-success\" onclick=\"reservationClicked(this)\"style=\"float : right\">Rezervisi</a>"
						+"	</div>"                  
						+"</div>"
						+"</div>";
	$("#adminItems").append(newAdminItem);
}
