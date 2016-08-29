//Calls KafkaServlet
xhrGet("KafkaServlet", function(responseText){
	// add to document
	var mytitle = document.getElementById('message');
	mytitle.innerHTML = responseText;

}, function(err){
	console.log(err);
});

var query = null;
var jsonResponse = null;
function postMessage(){
	
	query = document.getElementById('txtQuery').value;
	var responseDiv = document.getElementById('consumeResponse');
	responseDiv.innerHTML = "<p>waiting for consumer...</p>";
	var responseDiv = document.getElementById('produceResponse');
	responseDiv.innerHTML = "<p>waiting for producer...</p>";
	var postMessageBtn = document.getElementById('bluebtn');
	postMessageBtn.value='Sending ...';
	xhrPost("KafkaServlet", function(responseText){
        // fill in produced and consume message sections
        // responseText is an array of an produced then consumed message
        var response = responseText.split(",,");

        var produceDiv = document.getElementById('produceResponse');
        produceDiv.innerHTML = "<div class='message'><a>Query produced: </a><small class='code'>" +response[0]+ "</small> </div>";
        var consumeDiv = document.getElementById('consumeResponse');
        consumeDiv.innerHTML = "<div class='message'><a>Query consumed: </a><small class='code'>" +response[1]+ "</small> </div>";
        // reset button text
        postMessageBtn.value='Search';

		xhrGet("KafkaServlet", function(responseText){
			// add message to 'already consumed messages' section
			//var mytitle = document.getElementById('message');
			//mytitle.innerHTML = responseText;
			postMessageBtn.value = "Search";
			relayQuery(responseText);
		}
				
		, function(err){
			console.log(err);
		});
	}, function(err){
		console.log(err);
	});
	
}

function relayQuery(query){
	
	alert("Hey!:"+query);
	var data = {q:query}
	$.getJSON( "./ListenerServlet", data, function(response) {
		console.log(JSON.stringify(response));
		
		document.getElementById("results").innerHTML = "";
		 for (var i = 0; i < response.items.length; i++) {
			 	
		        var item = response.items[i];     
		        document.getElementById("results").innerHTML += "<h3>" + item.htmlTitle;
		        document.getElementById("results").innerHTML += '<a href="'+item.link+'">'+item.displayLink+'</a>';
		        document.getElementById("results").innerHTML += "<br>" + item.snippet + "<hr>";
		      }
		 });
}

//utilities
function createXHR(){
	if(typeof XMLHttpRequest != 'undefined'){
		return new XMLHttpRequest();
	}else{
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){}
		}
	}
	return null;
}
function xhrGet(url, callback, errback){
	var xhr = new createXHR();
	xhr.open("GET", url, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.timeout = 9000;
	xhr.ontimeout = errback;
	xhr.send();
}

function xhrPost(url, callback, errback){
	var xhr = new createXHR();
	url = url+"?query="+query;
	xhr.open("POST", url, true);
	xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('XMLHttpRequest ready state: ' + xhr.readyState + '. Service not available');
			}
		}
	};
	xhr.timeout = 10000;
	xhr.ontimeout = errback;
	xhr.send(query);
	
}

function parseJson(str){
	return window.JSON ? JSON.parse(str) : eval('(' + str + ')');
}
function prettyJson(str){
	// If browser does not have JSON utilities, just print the raw string value.
	return window.JSON ? JSON.stringify(JSON.parse(str), null, '  ') : str;
}
