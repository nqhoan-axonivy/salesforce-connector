package com.axonivy.connector.salesforce.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Opportunity;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertUtils {
//	public static List<Pet> convertStringToList(String jonStr) throws JsonMappingException, JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		List<Pet> pets = mapper.readValue(jonStr, new TypeReference<List<Pet>>(){});
//		Ivy.log().info("-----------------convert ne: " + pets.size());
//		
//		return pets;		
//	}
	
	public static Opportunity convertToOpportunity(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Opportunity opportunity = mapper.readValue( mapper.treeAsTokens(jsonNode), Opportunity.class);
		return opportunity;
	}
	
	public static List<Opportunity> convertToListOpportunity(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();	
		JsonNode node = jsonNode.findValue("records");
		
		List<Opportunity> opportunities = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<List<Opportunity>>(){});
		return opportunities;
	}
	
	public static Account convertToAccount(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Account acc = mapper.readValue( mapper.treeAsTokens(jsonNode), Account.class);
		return acc;
	}
	
	public static <T extends Object> T getObject(Class cls, T object) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return (T) cls.getDeclaredConstructor().newInstance(); 
	}
}
