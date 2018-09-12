/*$(document).ready(function(){
	console.log("Ucitavanje prijatelja");
	//loadPage
	loadPeople();
	
	
});


function loadPeople(){
	$.ajax({
		 url: "http://localhost:8080/user/displayUsers",
		method:"GET",
		success: function(data){
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
		if(data[i].type==="0"){ //are not friends
			newRow+="<td><button name='add'  class='btn btn-success dodaj' style='width:80px;' id=" + data[i].id + ">Add</button></td>";
		}else if(data[i].type==="1"){ //waiting
			newRow+="<td><button name='pending' class='btn btn-warning' style='width:80px;' disabled>Pending</button></td>";
		}else if(data[i].type==="2"){//allready friends
			newRow+="<td><button name='delete' class='btn btn-danger obrisi' style='width:80px;' id=" + data[i].id+ ">Delete</button></td>>";
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
	        			 $("#"+id).removeClass('btn btn-success dodaj').addClass('btn btn-warning');
	        			 $("#"+id).text('Pending');
	        			 $("#"+id).prop('disabled', true);
	        	},
	        error:function(){
    			console.log("Vec ste dodali za prijatelja");
        	}
		
	        });
	  }
	
});

$(document).on("click",".obrisi",function(event){
	 var retVal = confirm("Are you sure you want to remove from friends?");
	 if( retVal === true ){
	        var id = $(this).attr('id');
	        
	        formData= JSON.stringify({
	    		id: id
	         });
	        console.log("id"+id);
	        console.log("formData"+formData);
	        $.ajax({
	    		url:"http://localhost:8080/friendship/delete",
	    		method:"DELETE",
	    		data : formData,
	    		contentType : 'application/json',
	        	success: function(){
	        			console.log("RASKINUTO PRIJATELJSTVO");
	        			 $("#"+id).removeClass('btn btn-danger obrisi').addClass('btn btn-success');
	        			 $("#"+id).text('Add');
	        			
	        	}
	    	});
	   }
});

*/