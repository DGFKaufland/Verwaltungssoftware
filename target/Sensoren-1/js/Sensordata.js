$(function(){ 
	var host = window.location.origin;
	
///////// Navigation ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	$('.nav li a').click(function(){
        $('.nav li a').removeClass('active')
        $(this).addClass('active') 
    });

    	
	//Sensordaten in die Datenbank eintragen	
	$('#btnInsertSensordata').unbind('click').click(function(){	
        $('#anzfeld').load('Sensordata_WebContent/insertSensordata.html');
    });
	
	
	$('#btnInsertSensordataInDB').unbind('click').click(function(){		
		insertSensorData();
		$('#anzfeld').load('home.html');
	    });	 
	
	 function insertSensorData() {
		 
		 var logical_sensor_id = document.getElementById("logical_sensor_id").value;
	   	 var sensordata_value = document.getElementById("sensordata_value").value;

		 $.ajax({
           	 type: 'POST',
           	 contentType: 'application/json',
           	 url: host+'/Sensoren/services/sensordata/insertSensordata',
           	 dataType: "json",
           	 data: JSON.stringify({
           		"logical_sensor_id": logical_sensor_id,
           		"value": sensordata_value
           	 		}),
           	 success: function(data){
           		 alert("Sensordaten erfolgreich in DB eingetragen!");
           	 },
           	 error: function(textStatus){
           	 alert('Problem beim Schreiben von Sensordaten in die Datenbank: ' + textStatus);
           	 }
           	 });   	
		 
		 
	     }	
		 
//////////////////////////////
// ENDE  	 
        		
});

