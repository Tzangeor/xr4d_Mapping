package com.george.rest_api;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Random;

@Path("secured")
public class JsonResource {
	
	
	@Path("population/{type}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDataInJSON(@PathParam("type") String type, String data) throws JsonSyntaxException, JsonIOException, IOException, InterruptedException, ParseException
	
	{  
		Model mappingModel= null;
		String text=data;
	//	String currentLine, allfile="";
	//	Random rand=new Random();
	//	int r=rand.nextInt(100);
		System.out.println("we are in");
		System.out.println(type);
		System.out.println(text);
	 if (!text.isEmpty()) {
			
        if (type.equals("{visuals}")) {
				mappingModel=Population.buildVisuals(text);
				System.out.println(mappingModel);
        }		
		else if	(type.equals("{stress_level}")) {	
			mappingModel=Population.buildStressLvl(text);
			for (Statement st : mappingModel) {
				  System.out.println(st);
				}
		}
		else if	(type.equals("{text}")) {	
			mappingModel=Population.buildText(text);
			for (Statement st : mappingModel) {
				  System.out.println(st);
				}
		}
	 }
		
     //   if (mappingModel!=null) {
        	System.out.println("we are in model");
			
//			OutputStream out= new FileOutputStream("test"+r+".ttl");
//			Rio.write(mappingModel, out, RDFFormat.TURTLE);
//			out.close();
			
//			File file = new File("test\"+r+\".ttl");
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			while ((currentLine = br.readLine()) != null) {	
//				allfile=allfile+currentLine;
//				System.out.println(currentLine);
//			}
//			br.close();
//			System.out.println("test\"+r+\".ttl");
        	
//        	FileOutputStream out = new FileOutputStream("/home/tomcat/uploads/test.ttl");
//        			try {
//        			  Rio.write(mappingModel, out, RDFFormat.RDFXML);
//        			}
//        			finally {
//        			  out.close();
//        			}
        			
			
			GraphDB.add2(mappingModel);	
			//Files.deleteIfExists(Paths.get("test"+r+".ttl")); 
				return Response.status(201).entity("The data are mapped").build();
			//}
      
		//return Response.status(201).entity("The model is null!").build();	
	}
	
	
	
}
