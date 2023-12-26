package com.axonivy.connector.salesforce;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.axonivy.connector.salesforce.model.Opportunity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Path("salesforceMock")
@PermitAll
@Hidden
public class SalesforceMock {
	ObjectMapper mapper = new ObjectMapper();
	
	@GET
	@Path("query")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLists(@QueryParam("q") String q) {
		return Response.status(200).entity(load("json/oppLists.json")).build();
	}
	
	@POST
	@Path("sobjects/Opportunity")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewOpp(JsonNode jsonNode) {
		return Response.status(201).entity(load("json/createNewOppResponse.json")).build();
	}

	private static String load(String path) {
		try (InputStream is = SalesforceMock.class.getResourceAsStream(path)) {
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			throw new RuntimeException("Failed to read resource: " + path);
		}
	}
	
	
}
