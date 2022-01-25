package com.george.rest_api;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

import static org.eclipse.rdf4j.model.util.Values.iri;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


import java.util.UUID;


import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Population {

public static Model buildTest(String text) 	throws JsonSyntaxException, JsonIOException, IOException, ParseException
{
	
	System.out.println("we are in");
	//JSONParser parser = new JSONParser();
	Object obj=JSONValue.parse(text);
	JSONObject jsonObj= (JSONObject) obj;
	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
    String Id= jsonObj.get("id").toString();
    String StringTime=jsonObj.get("time").toString();
    LocalDateTime time = LocalDateTime.parse(StringTime,DATE_TIME_FORMATTER);
        
      //  System.out.println(name);   
     
        IRI ObservationInstance = Values.iri("http://www.semanticweb.org/giorgostzanetis/ontologies/2021/5/InitialxR4DRAMA", "Observation_1");
       
        ModelBuilder builder = new ModelBuilder();
        
        Model modelObservation= builder
        	  .setNamespace("xR","http://www.semanticweb.org/giorgostzanetis/ontologies/2021/5/InitialxR4DRAMA")
        	  .subject(ObservationInstance)
    		  .add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
    		  .add(RDFS.LABEL, "Observation_1")
    		  .add(RDF.TYPE,"xR:Observation")
    		  .add("ex:hasId", Id)
    		  .add("ex:hasTime", time)
    		  .build();
    		  	
	return builder.build();
}

public static Model buildStressLvl (String text) 	throws JsonSyntaxException, JsonIOException, IOException, ParseException
{
	System.out.println("we are in population");
	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	Object obj=JSONValue.parse(text);
	JSONObject jsonObj= (JSONObject) obj;

	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");   
    String id= jsonObj.get("User_ID").toString();
    String lvl=jsonObj.get("Stress_Level").toString();
    float lvlf=Float.parseFloat(lvl); 
    String StringTime=jsonObj.get("Timestamp").toString();
    String lat=jsonObj.get("Latitude").toString();
    String log=jsonObj.get("Longitude").toString();
    String ProjectId= jsonObj.get("Project_Id").toString();
    LocalDateTime time = LocalDateTime.parse(StringTime,DATE_TIME_FORMATTER);
    
    IRI ObservationInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Observation_"+uuid);
    IRI ResultInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Result_"+uuid);
    IRI FRInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "FR_"+id);
    IRI LocationInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Location_"+uuid);
    
    //String Graph="xR:graph"+uuid;
    ModelBuilder builder = new ModelBuilder();
    
    builder
    	.setNamespace("xR","http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA")
    //builder.namedGraph(Graph)
    	.subject(ObservationInstance)
    	  	.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
    	  	.add(RDFS.LABEL, "Observation_"+uuid)
    	  	.add(RDF.TYPE,"xR:#Observation")
    	  	.add("xR:isConsistedIn", "xR:Project_"+ProjectId)
    	  	.add("xR:hasTime", time)
    	  	.add("xR:hasResult", ResultInstance)
		.subject(ResultInstance)
		  	.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
		  	.add(RDFS.LABEL, "Result_"+uuid)
		  	.add(RDF.TYPE,"xR:#Result")
		  	.add("xR:hasStressLevel",lvlf)
		.subject(LocationInstance)
			.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
			.add(RDFS.LABEL, "Location_"+uuid)
		  	.add(RDF.TYPE,"xR:#Location")
		  	.add("xR:hasLatitude",lat )
		  	.add("xR:hasLongitude", log)
		.subject(FRInstance)
			.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
			.add(RDFS.LABEL, "FR_"+id)
			.add(RDF.TYPE,"xR:#FR")
			.add("xR:included","xR:Project_"+ProjectId)
			.add("xR:hasFRLocation", LocationInstance)
			.build();
  //  Model m = builder.build();
    return builder.build();	
 }

public static Model buildText (String text) 	throws JsonSyntaxException, JsonIOException, IOException, ParseException
{

	
	//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	ModelBuilder builder = new ModelBuilder();
	
	Instant instant = Instant.now();
	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").withZone( ZoneId.systemDefault() );
	String formater_output = DATE_TIME_FORMATTER.format( instant );
												
	
	Object obj=JSONValue.parse(text);
	JSONObject jsonObj= (JSONObject) obj;
	JSONObject json_meta= (JSONObject) jsonObj.get("meta");
	JSONObject json_data= (JSONObject) jsonObj.get("data");
	LocalDateTime current_time=  LocalDateTime.parse(formater_output,DATE_TIME_FORMATTER);
	String id=json_meta.get("id").toString().replaceAll("-", "");
	String projectId= json_meta.get("project_id").toString();
	String type= json_data.get("type").toString();
	
	IRI ObservationInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Observation_"+id);
	
	JSONArray objJASON_Array=(JSONArray) json_data.get("situations");
	
	builder
 	.setNamespace("xR","http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA")
 		.subject(ObservationInstance)
	 	.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
	  	.add(RDFS.LABEL, "Observation_"+id)
	  	.add(RDF.TYPE,"xR:#Observation")
	  	.add("xR:hasObservationType", type)
	  	.add("xR:hasCurrentTime", current_time)
	  	.add("xR:isConsistedIn", "xR:Project_"+projectId);
	
	for (int i = 0; i < objJASON_Array.size(); i++) {
		
		JSONObject json_sit=(JSONObject)objJASON_Array.get(i);
		
		if (json_sit.containsKey("location") && json_sit.containsKey("coordinates") ) {
			String locationURI=json_sit.get("location").toString();
			String coordinates=json_sit.get("coordinates").toString();
			String parts[] = coordinates.split("(?<= N)");
			String latitude= parts[0];
			System.out.println(latitude);
			String longtitude= parts[1]; 

			if (latitude !=null && longtitude!= null && locationURI !=null ) {  	
			
			IRI LocationInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Location_"+i);
			
			builder
			.subject(LocationInstance)
				.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
				.add(RDFS.LABEL, "Location_"+id)
			  	.add(RDF.TYPE,"xR:#Location")
			  	.add("xR:hasLatitude",latitude )
			  	.add("xR:hasLongitude", longtitude)
			  	.add("xR:hasURI", locationURI);
			}
		}
		
		if (json_sit.containsKey("risk_lvl")) {String risk_lvl=json_sit.get("risk_level").toString();}
		
		if ((json_sit.get("objectsFound"))!=null) { 	
			
		JSONArray objJASON_Array_2=(JSONArray) json_sit.get("affected_objects");
		for (int j = 0; i < objJASON_Array_2.size(); j++) {
			
			IRI InfoOfIntInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "InformationOfInterest"+ id +"_"+ i);
			
			String objects= objJASON_Array_2.get(j).toString();
			
			builder
			.subject(InfoOfIntInstance)
				.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
		  		.add(RDFS.LABEL, "InformationOfInterest"+ id +"_"+ j)
		  		.add(RDF.TYPE,"xR:#InformationOfInterest")
				.add("xR:hasType", objects)
				.add("xR:featureOf",ObservationInstance);
			
		}
		}	
	}

	 return builder.build();	
   
	
}

public static Model buildVisuals (String text) 	throws JsonSyntaxException, JsonIOException, IOException, ParseException
{
	System.out.println("we are in population");
	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	
	Instant instant = Instant.now();
	DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").withZone( ZoneId.systemDefault() );
	String formater_output = DATE_TIME_FORMATTER.format( instant );
	LocalDateTime current_time=  LocalDateTime.parse(formater_output,DATE_TIME_FORMATTER);
	
	Object obj=JSONValue.parse(text);
	JSONObject jsonObj= (JSONObject) obj;
	JSONObject json_header= (JSONObject) jsonObj.get("header");
	//JSONObject json_shotInfo= (JSONObject) jsonObj.get("shotInfo");
	
	String StringTime=json_header.get("timestamp").toString();
	LocalDateTime time = LocalDateTime.parse(StringTime,DATE_TIME_FORMATTER);
	String simmoid= json_header.get("simmoid").toString();
	String projectId= json_header.get("project_id").toString();
	
	ModelBuilder builder = new ModelBuilder();
	IRI ObservationInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Observation_"+uuid);
	IRI VideoInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "Video_"+uuid);
	
	builder
	.setNamespace("xR","http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA")
	.subject(ObservationInstance)
  		.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
  		.add(RDFS.LABEL, "Observation_"+uuid)
  		.add(RDF.TYPE,"xR:#Observation")
  		.add("xR:isConsistedIn", "xR:Project_"+projectId)
  		.add("xR:hasTime", time)
  		.add("xR:hasCurrentTime", current_time)
  		.add("xR:hasMultimedia", VideoInstance)
  	.subject(VideoInstance)
  		.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
  		.add(RDFS.LABEL, "Video_"+uuid)
  		.add(RDF.TYPE,"xR:#Video")
  		.add("xR:hasSIMMORef", simmoid);
	
	
	JSONArray objJASON_Array=(JSONArray) jsonObj.get("shotInfo");
	for (int i = 0; i < objJASON_Array.size(); i++) {
		
		IRI VisualMetaInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "VisualMetadata_"+ uuid +"_"+ i);
		
		JSONObject json_shot=(JSONObject)objJASON_Array.get(i);
		
		String fShotId= json_shot.get("shotIdx").toString();
		int shotId= Integer.parseInt(fShotId);
		String fPeople= json_shot.get("peopleInDanger").toString();
		int people= Integer.parseInt(fPeople);
		String fVehicles= json_shot.get("vehiclesInDanger").toString();
		int vehicles= Integer.parseInt(fVehicles);
		String fRiver= json_shot.get("riverOvertop").toString();
		boolean river=Boolean.parseBoolean(fRiver);
		String area= json_shot.get("area").toString();
		String fAreaProb= json_shot.get("areaProb").toString();
		float areaProb = 0; if ( fAreaProb == "none") { areaProb = 0;}  else {  areaProb= Float.parseFloat(fAreaProb);}
		String fOutdoor= json_shot.get("outdoor").toString();
		boolean outdoor=Boolean.parseBoolean(fOutdoor);
		String emergency= json_shot.get("emergencyType").toString();
		String fEmergProb= json_shot.get("areaProb").toString();
		float emergencyProb= Float.parseFloat(fEmergProb);
		
		builder
		.subject(VisualMetaInstance)
			.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
	  		.add(RDFS.LABEL, "VisualMetadata_"+ uuid +"_"+ i)
	  		.add(RDF.TYPE,"xR:#VisualMetadata")
	  		.add("xR:hasShotId",shotId)
	  		.add("xR:peopleInDanger", people)
	  		.add("xR:vehicleInDanger", vehicles)
	  		.add("xR:hasRiverOvertop", river)
	  		.add("xR:hasArea", area)
	  		.add("xR:hasAreaProb", areaProb)
	  		.add("xR:isOutdoor", outdoor)
	  		.add("xR:hasEmergencyProb", emergencyProb)
	  		.add("xR:hasEmergency",emergency)
	  	.subject(ObservationInstance)
	  		.add("xR:hasMetadata", "VisualMetadata_"+ uuid +"_"+ i);
		
		if ((json_shot.get("objectsFound"))!=null) {
			
		JSONArray objJASON_Array_2=(JSONArray) json_shot.get("objectsFound");
		
		
		for (int j = 0; j < objJASON_Array_2.size(); j++) {
			
			IRI InfoOfIntInstance = Values.iri("http://www.semanticweb.org/ontologies/2021/5/InitialxR4DRAMA", "InformationOfInterest"+ uuid +"_"+ i+j);
			
			JSONObject json_objects=(JSONObject)objJASON_Array_2.get(j);
			
			String type= json_objects.get("type").toString();
			String fObjectProb= json_objects.get("probability").toString();
			float ObjectProb= Float.parseFloat(fObjectProb);
			
			builder
			.subject(InfoOfIntInstance)
			.add(RDF.TYPE, OWL.NAMEDINDIVIDUAL)
	  		.add(RDFS.LABEL, "InformationOfInterest"+ uuid +"_"+ i+j)
	  		.add(RDF.TYPE,"xR:#InformationOfInterest")
			.add("xR:hasType", type)
			.add("xR:hasProbability", ObjectProb)
			.add("xR:featureOf",ObservationInstance);
		}
		}
	}
	return builder.build();
	
}		
	
}