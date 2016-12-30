package com.deductionapi.rest;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.deductionapi.database.DatabaseManager;
import com.deductionapi.marshaller.JsonManager;
import com.deductionapi.marshaller.XmlManager;
import com.deductionapi.model.DeductionItem;
import com.deductionapi.model.DeductionItemList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/DeductionService")
public class DeductionService {

	private XmlManager  xmlManager = new XmlManager();
	private JsonManager jsonManager = new JsonManager();
	private DatabaseManager dbManager = null;
	

	public DeductionService() {
		try {
			this.dbManager = new DatabaseManager("jdbc:mysql://localhost:3306/deductions_schema","root","welcome1");
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, "Exception trying to establish db connection");
			e.printStackTrace();
		}
	}
	
	
	
	@GET
	@Path("/testdb")
	public Response testdb() {

		String response = "No Response Available";
		try {
			List<DeductionItem> items = dbManager.getDeductionItems("SELECT *FROM deduction_item");
			response = xmlManager.getXmlStringFromDeductionItems(items);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(response).build();
	}
	



	@GET
	@Path("/getmessage")
	public Response getMsg() {

		String output = "Welcome to Deduction Service: " + new Date();

		return Response.status(200).entity(output).build();

	}
	
	@POST
	@Path("/saveitems")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveItems(@HeaderParam("Content-Type") String contentHeader,String jsonString) {
		String response = "no response to give you";
		if(contentHeader ==null || contentHeader.isEmpty() || contentHeader.equals("*/*")) {
			response = "Cant do nothing without a header";
		}
		if(contentHeader.equals("application/json")) {
			List<DeductionItem> list = jsonManager.getItemListFromJsonString(jsonString);
			try {
				dbManager.saveDeductionItems(list);
				return Response.status(200).entity("Saved data succesfully").build();
			} catch (SQLException e) {
				response = "There was a sql exception trying to save the data: "+e.getMessage();
				e.printStackTrace();
				return Response.status(500).entity(response).build();
			}
			
		}
		else {
			return Response.status(500).entity("Invalid Content-Type Header: "+contentHeader).build();
		}
	}

	
	@GET
	@Path("/removeitems")
	public Response removeItems() { 
		try {
			this.dbManager.removeAllItems();
			return Response.status(200).entity("Succesfully cleaned up the table").build();
		} catch (SQLException e) {
			return Response.status(500).entity("Exception trying to remove all items and clean the table").build();
		}
		
	}
	
	@GET
	@Path("/getdatafile")
	@Produces({ "application/xml", "application/json" })
	public Response getDataFile(@HeaderParam("Content-Type") String typeHeader) { 
		Logger.getGlobal().log(Level.SEVERE, "in getDataFile : "+typeHeader);
		
		final String header = typeHeader;
		StreamingOutput stream = new StreamingOutput() {
			
		    public void write(OutputStream os) throws IOException, WebApplicationException {

		      Writer writer = new BufferedWriter(new OutputStreamWriter(os));
		      List<DeductionItem> items = dbManager.getDeductionItems("SELECT *FROM deduction_item");
		      String response = "No contents in file";
		      //Logger.getGlobal().log(Level.SEVERE, "azept header: "+header);
		      
		      if (header.equals("application/xml")) {
					response = xmlManager.getXmlStringFromDeductionItems(items);
			  }
			  if (header.equals("application/json")) {
					response = jsonManager.getJsonStringFromDeductionItems(items);
			  }
		      writer.write(response);
		      writer.flush();
		    }
		  };
		  return Response.ok(stream).build();
		


	}
	
	@GET
	@Path("/getitems")
	@Produces({ "application/xml", "application/json" })
	public Response getItems(@HeaderParam("Accept") String acceptHeader) {
		Logger.getGlobal().log(Level.INFO, "In Get Items!!");
		List<DeductionItem> items = dbManager.getDeductionItems("SELECT *FROM deduction_item");
		String response = "No value to Give you!";
		if (acceptHeader.equals("application/xml")) {
			response = xmlManager.getXmlStringFromDeductionItems(items);
		}
		if (acceptHeader.equals("application/json")) {
			response = jsonManager.getJsonStringFromDeductionItems(items);
		}
		if (acceptHeader == null || acceptHeader.equals("*/*")) {
			response = "Cant do nothing without a header";
		}

		return Response.status(200).entity(response).build();
	}
	

	


}
