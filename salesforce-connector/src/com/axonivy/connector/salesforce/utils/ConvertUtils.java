package com.axonivy.connector.salesforce.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import com.axonivy.connector.salesforce.model.Account;
import com.axonivy.connector.salesforce.model.Event;
import com.axonivy.connector.salesforce.model.Opportunity;
import com.axonivy.connector.salesforce.model.OpportunityUpdateDTO;
import com.axonivy.connector.salesforce.model.Task;
import com.axonivy.connector.salesforce.response.CreateOppResponse;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;

import ch.ivyteam.ivy.process.call.SubProcessCall;

public class ConvertUtils {
	
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
	
	public static List<Account> convertToListAccounts(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();	
		JsonNode node = jsonNode.findValue("records");
		
		List<Account> accounts = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<List<Account>>(){});
		return accounts;
	}
	
	public static List<Task> convertToListTask(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();	
		JsonNode node = jsonNode.findValue("records");
		
		List<Task> tasks = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<List<Task>>(){});
		return tasks;
	}
	
	public static List<Event> convertToListEvent(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();	
		JsonNode node = jsonNode.findValue("records");
		
		List<Event> events = mapper.readValue(mapper.treeAsTokens(node), new TypeReference<List<Event>>(){});
		return events;
	}
	
	public static OpportunityUpdateDTO convertToOpportunityObjUpdate(Opportunity opportunity) throws IllegalAccessException, InvocationTargetException {
		OpportunityUpdateDTO dto = new OpportunityUpdateDTO();
		BeanUtils.copyProperties(dto, opportunity);
		return dto;
	}
	
	public static CreateOppResponse convertToOpportunityResponse(JsonNode jsonNode) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		CreateOppResponse opportunity = mapper.readValue( mapper.treeAsTokens(jsonNode), CreateOppResponse.class);
		return opportunity;
	}
	
	public static <T extends Object> T getObject(Class cls, T object) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return (T) cls.getDeclaredConstructor().newInstance(); 
	}
}
