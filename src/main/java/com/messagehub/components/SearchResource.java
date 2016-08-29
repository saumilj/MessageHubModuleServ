//package com.messagehub.components;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;
//
//
//@Path("/query")
//
//public class SearchResource {
//	
//	ClientClass c = new ClientClass();
//	
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getResult(@QueryParam("query") String query){
//		
//		return c.restCall(query);
//		
//	}
//}
