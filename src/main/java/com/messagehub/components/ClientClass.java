//package com.messagehub.components;
//
//import org.apache.wink.client.ApacheHttpClientConfig;
//
//import org.apache.wink.client.RestClient;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;
//
//import org.apache.wink.client.ClientConfig;
//import org.apache.wink.client.Resource;
//import javax.ws.rs.core.MediaType;
//import org.apache.wink.client.ClientResponse;
//
//	
//	
//@SuppressWarnings("deprecation")
//public class ClientClass {
//	
//	
//	public String restCall(String query){
//		
//		
//	ClientConfig clientConfig = new ApacheHttpClientConfig();
//	
//	
//	RestClient restClient = new RestClient(clientConfig);
//	
//	 
//	Resource restResource = restClient.resource("https://www.googleapis.com/customsearch/v1?q="+query+"&cx=017643444788069204610%3A4gvhea_mvga&key=AIzaSyBecg3fWUPbNMmPj3lkq_ey6A3IyEA90cU");
//		restResource.accept(MediaType.APPLICATION_JSON);
//		 
//		ClientResponse clientResponse = restResource.get();
//		 
//		int statusCode = clientResponse.getStatusCode();
//		String responseEntity = clientResponse.getEntity(String.class);
//		//JsonElement jelement = new JsonParser().parse(responseEntity.toString());
//	    
//		return responseEntity;
//		//System.out.print(responseEntity);
//	 }
//}
