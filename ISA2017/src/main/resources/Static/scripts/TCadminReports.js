$(document).ready(function(){
	
	google.charts.load('current', {packages: ['corechart', 'bar']});
	//google.charts.setOnLoadCallback(drawBasic);
	
	$("#getReportsButton").click(function(){
		$("#tab1").empty()
		$("#tab2").empty()
		//$("#tab3").empty()
		//$("#tab4").empty()
		
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
			
			$('#reportCinema').empty()
			$('#reportCinemaWeek').empty()
			$('#reportCinemaMonth').empty()
			$('#reportCinemaPeriod').empty()
			
			for(var i = 0; i < data.length; i++){
				cin = "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
				
				$('#reportCinema').append(cin)
				$('#reportCinemaWeek').append(cin)
				$('#reportCinemaMonth').append(cin)
				$('#reportCinemaPeriod').append(cin)
			}
			
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
			url : "http://localhost:8080/theatres/getTCadminTheatres"
		}).then(function(data){
			
			$('#reportTheatre').empty()
			$('#reportTheatreWeek').empty()
			$('#reportTheatreMonth').empty()
			$('#reportTheatrePeriod').empty()
			
			for(var i = 0; i < data.length; i++){
				cin = "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
				
				$('#reportTheatre').append(cin)
				$('#reportTheatreWeek').append(cin)
				$('#reportTheatreMonth').append(cin)
				$('#reportTheatrePeriod').append(cin)
			}
			
			cinemas = "<table class='table table-bordered'>" +
					"<tr>" +
					"<th>Pozoriste</th>" +
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
			url : "http://localhost:8080/movies/getTCadminMovies"
		}).then(function(data){
			
			movies = "<table class='table table-bordered'>" +
					"<tr>" +
					"<th>Film</th>" +
					"<th>Prosecna ocena</th>" +
					"</tr>"
					
			for(i = 0; i < data.length; i++){
				
				movies+="<tr>" +
						"<td>" + data[i].movieName + "</td>" +
						"<td>" + data[i].avgRating + "</td>" +
						"</tr>"
				
			}
			
			movies+="</table>"
				
			$("#tab2").append(movies)
			
		});
		
		$.ajax({
			url : "http://localhost:8080/plays/getTCadminPlays"
		}).then(function(data){
			
			plays = "<table class='table table-bordered'>" +
					"<tr>" +
					"<th>Predstava</th>" +
					"<th>Prosecna ocena</th>" +
					"</tr>"
					
			for(i = 0; i < data.length; i++){
				
				plays+="<tr>" +
						"<td>" + data[i].playName + "</td>" +
						"<td>" + data[i].avgRating + "</td>" +
						"</tr>"
				
			}
			
			plays+="</table>"
				
			$("#tab2").append(plays)
			
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
	
	$('#getDayReportBtn').click(function(){
		
		date = $('#reportDay').val()
		cinemaId = $('#reportCinema option:selected').attr('value')
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			date: dateFormat,
			projectionMovieCinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byDay",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date && data[j].time === data[i].time)
					        count++;
					}
					
					chartData.push([date, count])
							
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportDayChart', unique)
				
			}
		})
		
	})
	
	$('#getWeekReportBtn').click(function(){
	
		date = $('#reportWeek').val()
		cinemaId = $('#reportCinemaWeek option:selected').attr('value')
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		
		
		formData = JSON.stringify({
			dateBeggining: dateFormat,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byWeek",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date)
					        count++;
					}
					
					chartData.push([date, count])
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportWeekChart', unique)
				
			}
		})
		
	})
	
	$('#getMonthReportBtn').click(function(){
		
		date = $('#reportMonth').val()
		cinemaId = $('#reportCinemaMonth option:selected').attr('value')
		
		dateFormat = "01" + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			dateBeggining: dateFormat,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byMonth",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date)
					        count++;
					}
					
					chartData.push([date, count])
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportMonthChart', unique)
				
			}
		})
		
	})
	
	$('#getPeriodReportBtn').click(function(){
		
		dateFrom = $('#reportPeriodFrom').val()
		dateTo = $('#reportPeriodTo').val()
		cinemaId = $('#reportCinemaPeriod option:selected').attr('value')
		
		dateFormatFrom = dateFrom.split('-')[2] + "/" + dateFrom.split('-')[1] + "/" + dateFrom.split('-')[0] + " 00:00"
		dateFormatTo = dateTo.split('-')[2] + "/" + dateTo.split('-')[1] + "/" + dateTo.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			dateBeggining: dateFormatFrom,
			dateEnding: dateFormatTo,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byPeriod",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var sum = 0;
				
				for(var i = 0; i < data.length; i++){
					sum += Number(data[i].projectionPrice.split('din')[0])
				}
				
				$("#reportPeriodChart").empty().append("<h4>Zarada: " + sum + " dinara</h4>")
				
			}
		})
		
	})
	
	/*
	
									POZORISTA IZVESTAJI!!!										
												
	
																											*/
	
	$('#getDayReportTheatreBtn').click(function(){
		
		date = $('#reportDayTheatre').val()
		cinemaId = $('#reportTheatre option:selected').attr('value')
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			date: dateFormat,
			projectionMovieCinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byDayTheatre",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date && data[j].time === data[i].time)
					        count++;
					}
					
					chartData.push([date, count])
							
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportDayChartTheatre', unique)
				
			}
		})
		
	})
	
	$('#getWeekReportTheatreBtn').click(function(){
	
		date = $('#reportWeekTheatre').val()
		cinemaId = $('#reportTheatreWeek option:selected').attr('value')
		
		dateFormat = date.split('-')[2] + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		
		
		formData = JSON.stringify({
			dateBeggining: dateFormat,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byWeekTheatre",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date)
					        count++;
					}
					
					chartData.push([date, count])
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportWeekChartTheatre', unique)
				
			}
		})
		
	})
	
	$('#getMonthReportTheatreBtn').click(function(){
		
		date = $('#reportMonthTheatre').val()
		cinemaId = $('#reportTheatreMonth option:selected').attr('value')
		
		dateFormat = "01" + "/" + date.split('-')[1] + "/" + date.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			dateBeggining: dateFormat,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byMonthTheatre",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var chartData = []
				
				for(var i = 0; i < data.length; i++){
					
					var day = Number(data[i].date.split('/')[0])
					var month = Number(data[i].date.split('/')[1])
					var year = Number(data[i].date.split('/')[2])
					var hour = Number(data[i].time.split(':')[0])
					var minute = Number(data[i].time.split(':')[1])
					var date = new Date(year, month, day, hour, minute)
					
					var count = 0;
					for(var j = 0; j < data.length; j++){
					    if(data[j].date === data[i].date)
					        count++;
					}
					
					chartData.push([date, count])
				}
				
				var unique = multiDimensionalUnique(chartData)
				
				drawBasic('reportMonthChartTheatre', unique)
				
			}
		})
		
	})
	
	$('#getPeriodReportTheatreBtn').click(function(){
		
		dateFrom = $('#reportPeriodFromTheatre').val()
		dateTo = $('#reportPeriodToTheatre').val()
		cinemaId = $('#reportTheatrePeriod option:selected').attr('value')
		
		dateFormatFrom = dateFrom.split('-')[2] + "/" + dateFrom.split('-')[1] + "/" + dateFrom.split('-')[0] + " 00:00"
		dateFormatTo = dateTo.split('-')[2] + "/" + dateTo.split('-')[1] + "/" + dateTo.split('-')[0] + " 00:00"
		
		formData = JSON.stringify({
			dateBeggining: dateFormatFrom,
			dateEnding: dateFormatTo,
			cinemaId: cinemaId
		})
		
		$.ajax({
			url: "/reports/byPeriodTheatre",
			type: "POST",
			data: formData,
			contentType: "application/json",
			datatype: "json",
			success: function(data){
				
				var sum = 0;
				
				for(var i = 0; i < data.length; i++){
					sum += Number(data[i].projectionPrice.split('din')[0])
				}
				
				$("#reportPeriodChartTheatre").empty().append("<h4>Zarada: " + sum + " dinara</h4>")
				
			}
		})
		
	})
	
});

function onlyUnique(value, index, self) { 
    return self.indexOf(value) === index;
}

function multiDimensionalUnique(arr) {
    var uniques = [];
    var itemsFound = {};
    for(var i = 0, l = arr.length; i < l; i++) {
        var stringified = JSON.stringify(arr[i]);
        if(itemsFound[stringified]) { continue; }
        uniques.push(arr[i]);
        itemsFound[stringified] = true;
    }
    return uniques;
}

function drawBasic(chartId, chartData) {

    var data = google.visualization.arrayToDataTable([
      ]);
    
    data.addColumn('date', 'Datum')
    data.addColumn('number', 'Posete')
    
    for(var i = 0; i < chartData.length; i++){
    	row = [chartData[i][0], chartData[i][1]]
    	data.addRow(row)
    }

	var options = {
		hAxis: { textPosition: 'none' }
	};


    var chart = new google.visualization.ColumnChart(document.getElementById(chartId));

    chart.draw(data, options);
  }