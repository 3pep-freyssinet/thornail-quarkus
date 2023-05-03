package com.example.demo.rest;

import javax.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.annotations.Form;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "images")
@org.hibernate.annotations.NamedQueries({
    @org.hibernate.annotations.NamedQuery(name = "MyEntity.findAll", query = "SELECT e FROM MyEntity e"),
    @org.hibernate.annotations.NamedQuery(name = "MyEntity.findId", query = "SELECT e FROM MyEntity e WHERE e.id = :id"),
	@org.hibernate.annotations.NamedQuery(name = "MyEntity.findName", query = "SELECT e FROM MyEntity e WHERE e.name = :name"),
	
	@org.hibernate.annotations.NamedQuery(name = "MyEntity.removeId", query = "DELETE FROM MyEntity e WHERE e.id = :id"),
	@org.hibernate.annotations.NamedQuery(name = "MyEntity.removeName", query = "DELETE FROM MyEntity e WHERE e.name = :name")
	
})
@XmlRootElement
public class MyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
    //@FormParam("name")   //fourni par le formulaire dans input name='name'
	@PartType("text/plain")
    public String name;
	
	//@FormParam("uploadedFile")     //input type='file' name='uploadedFile'
    @PartType("application/octet-stream")
	public byte[] filedata;
	
	//Constructor
	public MyEntity(){}
	
	public MyEntity(String name, byte[] filedata){
		this.name 	  = name;
		this.filedata = filedata;
	}

	public int getId() {
		return id;
	}
  
	public void setId(Integer i) {
		id = i;
	}


	public String getNname() {
        return name;
	}
	
	public void setName(String s) {
        this.name = s;
    }
    
    public byte[] getFiledata() {
        return filedata;
    }

    //@FormParam("filedata")
    //@PartType("application/octet-stream")
    public void setFiledata(final byte[] filedata) {
        this.filedata = filedata;
    }
}