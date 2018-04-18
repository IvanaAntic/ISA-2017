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
			for (i = 0; i < data.length; i++) {
					
				appendUserItem(data[i]);				
				
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
			for (i = 0; i < data.length; i++) {				
				
				appendMyUserItem(data[i]);				
				
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
							+"	<td id=\"itemBidderName\">"+bids[i].buyerName+"</td>"
							+"	<td id=\"itemBidPrice\">"+bids[i].price+"</td>"
							+"	<td id=\"itemBidId\" style=\"display:none;\">"+bids[i].id+"</td>"
							+"	<td id=\"itemBidderId\" style=\"display:none;\">"+bids[i].buyerId+"</td>"
							+"</tr>";
					$("#myUserItemBidsTable").append(bidRow);
				}
				bestBidRow = "<tr>"						
							+"	<td id=\"itemBidderName\">"+maxBid.buyerName+"</td>"
							+"	<td id=\"itemBidPrice\">"+maxBid.price+"</td>"
							+"	<td id=\"itemBidId\" style=\"display:none;\">"+maxBid.id+"</td>"
							+"	<td id=\"itemBidderId\" style=\"display:none;\">"+maxBid.buyerId+"</td>"
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
function getAdminItems(){
	
	$.ajax({
		method : 'GET',
		url : urlBase + "/FanZone/adminItemsForSale",
		contentType: "application/json",
		datatype: 'json',
		success : function(data){
			console.log(data);
			for (i = 0; i < data.length; i++) {
				
				appendAdminItem(data[i]);				
				
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
function appendMyUserItem(data){
	
	var myUserItem = "<div id=\""+data.id+"\" class=\"card mt-4\">"
					+"<div class=\"card-body\">" 
					+"		<div class=\"col-lg-6\">"
					+"			<img class=\"card-img img-fluid mt-3\" src=\"http://via.placeholder.com/250x250\" >"
					+"		</div>"                 
					+"		<div class=\"col-lg-6\">"
					+"		<h3 id=\"myUserItemName\" class=\"card-title\">"+data.name+"</h3>"
					+"		<p class=\"card-text\">"+data.description+"</p>"
					+"	<table class=\"table\">"
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
					+"		<button id=\""+data.id+"\" class=\"btn btn-primary ml-2 \" onclick=\"getMyItemBids(this)\" type=\"button\">Ponude</button>"
					+"     </span>"
					+"    </div>"			       
					+"		</div>"
					+"	</div>"                  
					+"</div>";
					
	$("#myUserItems").append(myUserItem);
	
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
						+"			<a class=\"btn btn-success\" style=\"float : right\">Rezervisi</a>"
						+"	</div>"                  
						+"</div>"
						+"</div>";
	$("#adminItems").append(newAdminItem);
}
