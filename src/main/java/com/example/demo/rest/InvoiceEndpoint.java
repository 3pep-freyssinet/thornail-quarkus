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
//import org.apache.maven.util.Base64;
import java.io.IOException;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

//import org.postgresql.Driver;

//import retrofit2.http.Headers;

//@ApplicationScoped
@Path("/invoice")


public class InvoiceEndpoint {

  //@PersistenceContext
  //@PersistenceContext(unitName = "MyPU")

  
  @Inject
  EntityManager em;

  //the name of the author.The curl query is like : curl http://localhost:8080/books/author/toto/hello_the_world, where 'titi' is the name of the author
  @GET
  @Path("/")
  @Produces("text/plain")
  public Response grettings() {
    return Response.ok("Hello world from invoice").build();
  }

  
  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)

  public Invoice[] all() {
    
	/*
	try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException ex)
        {
			System.out.println("****************************************** ClassNotFoundException = "+ex);
        }

	
	InitialContext ctx_PrgSql = null;
        java.sql.Connection pcon = null;
        try
        {
            ctx_PrgSql = new InitialContext();
            DataSource ds_PrgSql = (DataSource) ctx_PrgSql.lookup("java:jboss/datasources/ExampleDS");
			
			
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyPU");
			em = factory.createEntityManager();
			
            System.out.println("****************************************** ds_PrgSql = "+ds_PrgSql+" factory = "+factory+"em = "+em);
			
			if (ds_PrgSql == null)
            {            System.out.println("****************************************** ds_PrgSql = "+ds_PrgSql);
            }
	    }catch (NamingException ex)
        {               System.out.println("****************************************** NamingException = "+ex);
        }
		//pcon = ds_PrgSql.getConnection();
		//System.out.println("****************************************** pcon = "+pcon);
	
	//DataSource ds = (DataSource) context.lookup("java:jboss/datasources/MyDS");
		*/
		
	//return em.createNamedQuery("Book.findAll", Book.class)
	//Query query = em.createQuery("SELECT e FROM Book e");
	//System.out.println("******************************************");
	//return null;
	//return query.getResultList()
    //   .toArray(new Book[0]);
	   //Query query = em.createNamedQuery("Book.findAll");
	   
	   //System.out.println("****************************************** em = "+em);
	   
	//return em.createNamedQuery("Book.findAll")
    //    .getResultList()
    //    .toArray(new Book[0]);
	
	//EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyPU");
	
	//Metamodel metamodel = emf.getMetamodel();
	//EntityType entityType = metamodel.entity(Book.class);
    //String name = entityType.getName();
	//System.out.println("****************************************** metamodel = "+metamodel+" entityType = "+entityType+" name = "+name);
	
	//EntityType et = emf.getMetamodel().entity(Book.class);
    //String name = et.getName();
	//System.out.println("****************************************** name = "+name);
	
	//em = emf.createEntityManager();
	//System.out.println("****************************************** em = "+em);
	
		//Set<EntityType<?>> entityTypes = emf.getMetamodel().getEntities();
        //EntityType et = emf.getMetamodel().entity(Book);
        //et.getName();
	//System.out.println("****************************************** em = "+em);
	//em.getMetamodel().managedType(Book.class);
	//em.getMetamodel().managedType();
	
	Query query = em.createNamedQuery("Invoice.findAll");			//em.createQuery("SELECT e FROM Invoice e");	
	//System.out.println("****************************************** query = "+query);
	
	List<Invoice> list = query.getResultList();
    //System.out.println("****************************************** list = "+list+" size = "+list.size());
	
	//Invoice Invoice = (Invoice)list.get(0);
	//System.out.println("****************************************** Invoice = "+Invoice);
	
	//for (Invoice invoice : list) {
    //  System.out.println(invoice.getId());
    //}
	
    //****************************************************************************
	/*
	Either:

	Foo[] array = list.toArray(new Foo[0]);
	or:

	Foo[] array = new Foo[list.size()];
	list.toArray(array); // fill the array
	Note that this works only for arrays of reference types. For arrays of primitive types, use the traditional way:

	List<Integer> list = ...;
	int[] array = new int[list.size()];
	for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
	Update:
	It is recommended now to use list.toArray(new Foo[0]);, not list.toArray(new Foo[list.size()]);.
	*/
	//****************************************************************************
	
	return list.toArray(new Invoice[0]);
	
  }
   
  //used in new
  @GET
  @Path("/{id}") //the 'id' of the record.The curl query is like : curl http://localhost:8080/invoice/99, where '99' is the id of the record.
  @Produces(MediaType.APPLICATION_JSON)
  public Invoice getId(@PathParam("id") String id){
	//in 'findId' id is an integer not string
	int id_ = Integer.parseInt(id);
	return (Invoice)em.createNamedQuery("Invoice.findId") //em.createNamedQuery("Invoice.findId", Invoice.class)
		.setParameter("id", id_)
        .getResultList()
        .get(0);
  }

  
  //get id or date 
  @GET
  @Path("/{param0}/{param1}")   //par exemple : /id/99 ou /date/01-01-70
  @Produces(MediaType.APPLICATION_JSON)
  public Invoice getInvoice(@PathParam("param0") String param0, @PathParam("param1") String param1){
    if(param0.equals("id"))
		return (Invoice)em.createNamedQuery("Invoice.findId") //em.createNamedQuery("Invoice.findId", Invoice.class)
        .setParameter("id", param1)
        .getResultList()
        .get(0);		//one element in the list for isbn
	if(param0.equals("date"))
		return (Invoice)em.createNamedQuery("Invoice.findDate")
		.setParameter("date", param1)
        .getResultList()
        .get(0);		//one element in the list for author
		
	return null; // no id or date found
  }
  
  /*  
	  curl -H "Content-Type: application/json" -d "{\"elec_index\":\"1000\",\"gas_index\":\"2000\", \"date\":\"01-01-70\"}" -X POST http://localhost:8080/invoice/new
  */
  
  
  @POST
  @Path("/new")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Invoice post(Invoice invoice){
    System.out.println("********************************************************************************************************************");
	System.out.println("*******invoice = " + invoice);
	System.out.println("*******new, invoice, elec_index = " + invoice.getElec_index() + " gas_index= " + invoice.getGas_index() + " date = " +invoice.getDate() );
	System.out.println("********************************************************************************************************************");
	
	em.persist(invoice);
	String id_ = String.valueOf(invoice.getId());//get the id from the perisist.
	System.out.println("****************************************** new, invoice, id after persist = " + id_);
    return getId(id_); //call the above method 'getId' with argument from the persist.
  }
   
	
	/*
  @DELETE //pour voir l'effet de delete avec curl, remplacer '@DELETE' par '@GET'
  //@GET
  @Path("/delete/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response DeleteId(@PathParam("id") int id){
    //Query query  = em.createNamedQuery("Book.findId", Book.class);
	//Query query_ = em.createNamedQuery("Book.findId");
	//System.out.println("****************************************** delete, query = " + query + " query_ = " + query_);
	
	Book book = em.createNamedQuery("Book.findId", Book.class).setParameter("id", id).getResultList().get(0);
	//System.out.println("****************************************** delete, number = " + number);
	if(book != null)em.remove(book);
	return Response.ok("delete Id " + id + " success").build();
	
	//autre methode
	//Bulk queries like update and delete cannot be used in TypedQuery. TypedQuery est le 2ème argument de createNamedQuery. donc utiliser createNamedQuery avec un seul argument.
	//int number = em.createNamedQuery("Book.removeId").setParameter("id", id).executeUpdate();  //Number of entities deleted 
    //System.out.println("****************************************** delete, number = " + number);
	//if(number != 1) return Response.ok("delete entities " + number + " failure").build();   
	//return Response.ok("delete entity " + number + " success").build();
  }
  */
  
  /*
  //  curl -H "Content-Type: application/json" -d "{\"isbn\":\"foofoo\",\"author\":\"someone_anyone\", \"id\":\"92\"}" -X POST http://localhost:8080/books/update
  @POST
  @Path("/update/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response updateId(Book book){
    System.out.println("****************************************** put, id = " + book.getId() + " isbn = " + book.getIsbn() + " author = " + book.getAuthor() );
	
	Query query = em.createNamedQuery("Book.findId");
	
	//save the values sent in the POST
	int id0         = book.getId();
	String author0  = book.getAuthor();
	String isbn0    = book.getIsbn();
	
	Query query_    =  query.setParameter("id", id0);
	System.out.println("****************************************** put, id = " + id0 + " isbn = " + isbn0 + " author = " + author0 + " query = " + query_);
	List<Book> list = query_.getResultList();
	book            = (Book)list.get(0);
	
	if(book == null)return Response.ok("book with id = " + id0 + " not found").build();;
	
	//get the current values
	String author1  = book.getAuthor();
	String isbn1    = book.getIsbn();
	System.out.println("****************************************** put, current book = " + book + " id = " + id0 + " isbn = " + isbn1 + " author = " + author1);
	
	//update
	book.setAuthor(author0);
	book.setIsbn(isbn0);
	System.out.println("****************************************** put, updated book = " + book + " id = " + id0 + " isbn = " + book.getIsbn() + " author = " + book.getAuthor());
	
	em.merge(book);
	return Response.ok("update Id "+id0+" author = "+author1+" isbn = "+isbn1+" updated to author = "+author0+" isbn = "+isbn0+" with success.").build();
  }
  */
  
  /* old comment
  @GET
  @Path("/author")
  @Produces(MediaType.APPLICATION_JSON)
	public Response getListOfBooks(@HeaderParam("Accept-Language") String acceptLanguage,
            @HeaderParam("User-Agent") String userAgent, @HeaderParam("Name") String name){
		return Response.status(200)
                  .entity("User-Agent :- " + userAgent + " \nAccept-Language :- " + acceptLanguage + " \nName :- " + name)
                      .build();
	}
	*/
 
 /*
  @POST
  @Path("/authorization/{message}")
  //@Path("/authorization/titi/{message}")
  //@Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  //@Produces("text/plain")
    public Response valideAutorization(@HeaderParam("Authorization") String authorization, @PathParam("message") String message){
		//public Response valideAutorization(@PathParam("message") String message){
		//return Response.ok("/autorization/titi/ et POST message = " + message + " authorization reçu = " + authorization).build();
		//return Response.status(Response.Status.OK).entity(message).build();
		
		System.out.println("authorization = " + authorization);//authorization = Basic YXplcnR5OnF3ZXJ0eQ==
		
		String identifier[] = authorization.split(" ");
		
		System.out.println("identifier[0] = " + identifier[0].trim() + " identifier[1] = " + identifier[1].trim());
		
        System.out.println("size = " + em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().size());
		
		if( em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().size()!= 1)return Response.status(Response.Status.EXPECTATION_FAILED).entity("identifier error").build();
		
		Book b = (Book)em.createNamedQuery("Book.findAuthor").setParameter("author", identifier[0].trim()).getResultList().get(0);	
		
		System.out.println("**************** book = " + b);
		
		if(b.equals(null))return Response.status(Response.Status.EXPECTATION_FAILED).entity("identifier error").build();
		
		System.out.println("**************** author = "+b.getAuthor()+" isbn = "+b.getIsbn());
		
		if(identifier[1].trim().equals(b.getIsbn())) return Response.status(Response.Status.OK).entity("success").build();
		
		//if(identifier[0].trim().equals("titi") && identifier[1].trim().equals("toto"))return Response.status(Response.Status.OK).entity("success").build();
			
		return Response.status(Response.Status.EXPECTATION_FAILED).entity("pwd error").build();
		//String json = //convert entity to json
		//return Response.ok(json, MediaType.APPLICATION_JSON).build();	
	}
	*/
	
	/*
   @POST
   @Transactional
    @Path("/upload/{id}")  //Your Path or URL to call this service
    
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes("multipart/form-data")

    public void upload(@MultipartForm MyEntity entity, @PathParam("id") int id) throws IOException {
	
		System.out.println("******************************** entity = "+entity+" id = "+id+" name = "+entity.name+" bytes = "+entity.filedata+" length = "+ entity.filedata.length);
		byte[] filedata = entity.getFiledata();
		System.out.println("******************************** filedata = "+filedata);
		
	    System.out.println("*********************************** entity manager upload image = "+em);
		
		em.persist(entity);
		
		System.out.println("*********************************** entity manager upload id = "+entity.getId());
		//return Response.ok("MultipartForm!************").build();
	
	//JsonObject jsonFile = Json.createObjectBuilder()
                //.add("length", entity.getFile().length)
                //.add("file", entity.getData().toString())
                //.build();
 
        //return Response.ok(jsonFile).build();
    }
	*/
	
	/*
	@GET
	@Path("/imagesall")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity[] imagesAll() {
    
	Query query = em.createNamedQuery("MyEntity.findAll");			//em.createQuery("SELECT e FROM MyEntity e");	
	//System.out.println("****************************************** query = "+query);
	
	List<MyEntity> list = query.getResultList();
    
	return list.toArray(new MyEntity[0]);
	
  }
  */
  
  /*
  @GET
	@Path("/imageid/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	
	public MyEntity getImageId(@PathParam("id") int id){
    return (MyEntity)em.createNamedQuery("MyEntity.findId") 
		.setParameter("id", id)
        .getResultList()
        .get(0);
  }
  */
  
  /*
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
	*/
}
