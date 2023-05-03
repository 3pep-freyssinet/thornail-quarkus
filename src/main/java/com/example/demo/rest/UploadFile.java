package com.example.demo.rest;

import com.example.demo.rest.Book;
import com.example.demo.rest.MyEntity;

import javax.enterprise.context.ApplicationScoped;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Persistence;

import javax.transaction.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import javax.persistence.Query;
//import javax.persistence.metamodel.Metamodel;
//import javax.persistence.metamodel.EntityType;
import javax.inject.Inject;

import java.util.List;
import java.util.Base64;
import java.util.List;
import java.util.Map;
//import org.apache.maven.util.Base64;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.nio.charset.StandardCharsets;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

//import org.postgresql.Driver;

//import retrofit2.http.Headers;

//@ApplicationScoped
@Path("/images")


public class UploadFile {

  //@PersistenceContext
  //@PersistenceContext(unitName = "MyPU")

  
  @Inject
  EntityManager em;

    /*
	@GET
	@Path("/download/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity getImageId(@PathParam("id") int id){
    return (MyEntity)em.createNamedQuery("MyEntity.findId") 
		.setParameter("id", id)
        .getResultList()
        .get(0);
  }
   */

   @POST
   @Transactional
    @Path("/upload")  //upload from client to server.
    
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes("multipart/form-data")
    //@Consumes(MediaType.MULTIPART_FORM_DATA+";charset=UTF-8")

	public Response upload(MultipartFormDataInput input){

    //public void upload(@MultipartForm MyEntity entity) throws IOException {
	
	
	Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
	//liste des "part" dans l'ordre d'envoi
	List<InputPart> inputPart0 = uploadForm.get("name"); 	//correspondance avec Call<ResponseBody> uploadImage(@Part("name") RequestBody items, @Part MultipartBody.Part user, @Part MultipartBody.Part image);
	List<InputPart> inputPart1 = uploadForm.get("tata");	//correspondance avec MultipartBody.Part user = MultipartBody.Part.createFormData("tata", "tartar");
	List<InputPart> inputPart2 = uploadForm.get("image"); 	//correspondance avec MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", req
	
	String name = "";
	byte[] fileData = null;
	try {
		byte[] surnameb = inputPart0.get(0).getBody(byte[].class, null);
		String surname  = new String(surnameb,  StandardCharsets.UTF_8);
		
		byte[] nameb = inputPart1.get(0).getBody(byte[].class, null);
		name  = new String(nameb,  StandardCharsets.UTF_8);
		
		fileData = inputPart2.get(0).getBody(byte[].class, null);
		
		
		System.out.println("****name= "+surname+" tata = "+name+" filedata length = "+fileData.length);
	} catch (IOException e) {
            e.printStackTrace();
    }
	
	System.out.println("********************************************************************************************************************************");
	System.out.println("*********************************** entitymanager from UploadFile = "+em);
	System.out.println("********************************************************************************************************************************");
	
	MyEntity entity = new MyEntity(name, fileData);
	
	//Persist the entity
	em.persist(entity);
	
	//Save the image bytes in fileData
	File file = new File("D:\\demo\\demo-upload.jpg"); 
	try {
		OutputStream os = new FileOutputStream(file); 
                 
		// Starts writing the bytes in it 
		os.write(fileData); 
		System.out.println("Successfully "+fileData.length+" bytes inserted in file = "+file); 
  
		// Close the file 
        os.close(); 
        } catch (Exception e) { 
            System.out.println("Exception: " + e); 
        } 
     
	
	return Response.ok("upload success.").build();
	
}
	
	@GET
	@Path("/imagesall")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity[] imagesAll() {
    
	Query query = em.createNamedQuery("MyEntity.findAll");			//em.createQuery("SELECT e FROM MyEntity e");	
	//System.out.println("****************************************** query = "+query);
	
	List<MyEntity> list = query.getResultList();
    
	return list.toArray(new MyEntity[0]);
	
  }
  
  @GET
	@Path("/imageid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	//The return "MyEntity" will be applied JSON and bundled. In the client side, it will mapped to a POJO "MyEntity" where the property "filedata" will be
	// a String type since JSON cannot serialize array[] and also it will be coded Base64.
	
	public MyEntity getImageId(@PathParam("id") int id){
    System.out.println("********************************** imageid/{id} id = "+id);
	MyEntity myEntity = (MyEntity)em.createNamedQuery("MyEntity.findId") 
		.setParameter("id", id)
        .getResultList()
        .get(0);
	System.out.println("********************************** myEntity = "+myEntity);
	byte[] bytes = myEntity.filedata;
	
	System.out.println("********************************** myEntity.filedata = "+myEntity.filedata+" bytes = "+bytes+" length = "+bytes.length+" bytes(0) = "+bytes[0]);
	
	
	//Save the image bytes in external disk.
	File file = new File("D:\\demo\\demo-download.jpg"); 
	try {
		OutputStream os = new FileOutputStream(file); 
                 
		// Starts writing the bytes in it 
		os.write(myEntity.filedata); 
		System.out.println("Successfully "+myEntity.filedata.length+" bytes inserted in file = "+file); 
  
		// Close the file 
        os.close(); 
        } catch (Exception e) { 
            System.out.println("Exception: " + e); 
        } 
		
	return myEntity;
  }
  
  @GET
	@Path("/imagebyteid/{id}")
	//@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/octet-stream") // ouverture fenêtre téléchargement
	@Produces("application/html")    //fenêtre téléchargement
	
	public String getImageByteId(@PathParam("id") int id){
		byte[] bytes = getImageId(id)
		              .getFiledata(); 
		
		
		String base64String = Base64.getEncoder().encodeToString(bytes);
		System.out.println("****************************************** base64String = "+base64String);
		return base64String;
		//byte[] backToBytes = Base64.decode(base64String);
		
  }
  
	
}
