$(document).ready(function(){
	console.log("Ucitavanje prijatelja");
	loadPeople();
	
	
});


function loadPeople(){
	$.ajax({
		 url: "http://localhost:8080/user/displayUsers",
		method:"GET",
		success: function(data){
			//load row
			console.log("Uspeh");
			  $(".peopleTable").empty();
			  displayListOfPeople(data);
		}
	});
}

function displayListOfPeople(data){
	console.log(data);
	for(i=0;i<data.length;i++){
		newRow="<tr>"
			+"<td>"+data[i].name+"</td>"
			+"<td>"+data[i].surname+"</td>";
		console.log(data[i].type);
		
		if(data[i].type==="0"){ //are not friends
			newRow+="<td><button name='add'  class='btn btn-success dodaj' style='width:80px;' id=" + data[i].id + ">Add</button></td>";
			
		}else if(data[i].type==="1"){ //waiting
			newRow+="<td><button name='pending' class='btn btn-warning' style='width:80px;' disabled>Pending</button></td>";
		}else if(data[i].type==="2"){//allready friends
			newRow+="<td><button name='delete' class='btn btn-danger' style='width:80px;' id=" + data[i].id+ ">Delete</button></td>>";
		}
		newRow+="</tr>";
		$(".peopleTable").append(newRow);
	}
	
}

$(document).on('click','.dodaj',function(event){
	 var retVal = confirm("Are you sure you want to add a friend?");
	 if( retVal === true ){
	        var id = $(this).attr('id');
	        formData= JSON.stringify({
	    		id: id
	              });
	        console.log(id);
	        console.log(formData);
	        $.ajax({
	        	url: "http://localhost:8080/friendship/addFriend",
	        	method: "POST",
	        	data : formData,
	        	contentType : 'application/json',
	        	success: function(){
	        			console.log("USPesnoste dodaliii Prijatelja");
	        			  $(this).removeClass('btn btn-success dodaj').addClass('btn btn-warning');
	                      $(this).text('Pending');
	                      $(this).prop('disabled', true);
	        	},
	        error:function(){
    			console.log("Vec ste dodali za prijatelja");
        	}
		
	        });
	  }
	
});

function formToJSON(idFriend){
	function formToJSON(idFriend) {
	    return JSON.stringify({
	        "id" : id
	    });
	}
}
