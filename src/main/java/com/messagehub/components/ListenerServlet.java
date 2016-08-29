package com.messagehub.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;

/**
 * Servlet implementation class Servlet3
 */
@WebServlet("/ListenerServlet")
public class ListenerServlet extends HttpServlet {
	
	String query="";
	private String search;
	
	Producer producer;
//	String keyValue = "";
//	InputStream inputStream;
//	
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ListenerServlet() {
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		query = request.getParameter("q");
		String queries="";
		
		// Create HTTP connection with the given URL
		
		// Support multiple words
		
        String[] queryArray = query.split("\\s*(=>|,|\\s)\\s*");
        
        for(int i=0;i<queryArray.length-1;i++){
        	queries += queryArray[i]+"+";
        }
        queries+=queryArray[queryArray.length-1];
     
		String google = "https://www.googleapis.com/customsearch/v1?q="+queries+"&cx=017643444788069204610%3A4gvhea_mvga&key=AIzaSyBecg3fWUPbNMmPj3lkq_ey6A3IyEA90cU";
		URL url = null;
		HttpURLConnection connection = null;
		url = new URL(google);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Access-Control-Allow-Origin", "*");
	    
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
	    while ((line = rd.readLine()) != null) {
	    	sb.append(line);	
	    }
	    
	    // Receive JsonElement and send it as response to jquery
	    JsonElement jelement = new JsonParser().parse(sb.toString());
	    System.out.println(sb.toString());
	    out.println(jelement);
	    sb.setLength(0);
	    query = "";
	    queries = "";
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	      response.setContentType("text/html");
	     
	}
	
}
