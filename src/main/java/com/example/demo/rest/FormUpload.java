package com.example.demo.rest;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;


@Path("/")
public class FormUpload {
    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from Thorntail!").build();
    }
	
	@GET 
	@Path("/{myparam}")
    @Produces("text/html")
    public String getHtml(@PathParam("myparam") String myparam) {
		
		//application/x-www-form-urlencoded multipart/form-data text/plain
		String s = "<html>\n";
        
        s+= "<body>\n<h1>JAX-RS Upload Form : "+myparam+"</h1>\n";
        //s+= "<form action='books/upload/999' method='post' enctype='multipart/form-data' Content-type='multipart/form-data'>\n";
		s+= "<form action='books/upload/999' method='post' enctype='multipart/form-data'>\n";
        s+= "<p><input name='name' type='text' value='Frank' /></p>\n";
		s+= "<p>Select a file : <input type='file' name='uploadedFile' size='50'/></p>\n";
        s+= "<input type='submit' value='Upload It'/>\n";
        s+= "</form>\n";
		s+= "</body>\n";
		s+= "</html>";
		
		return s;
    }
}
