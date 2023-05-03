package com.example.demo.rest;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
//import javax.ws.rs.Produces;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import javax.inject.Inject;

@Path("/hello")
public class HelloWorldEndpoint {
   
    @Inject
	EntityManager em;
    //LocalDateTime dateTime;

    @GET
    @Path("/datetime")
    //@Produces(MediaType.TEXT_PLAIN)
    public String getTime() {
        return em.toString();
    }
}
