package com.salesforce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;

import ch.ivyteam.ivy.bpm.error.BpmError;
import ch.ivyteam.ivy.environment.Ivy;
import io.swagger.v3.oas.annotations.Hidden;

@PermitAll
@Hidden
@SuppressWarnings("unused")

public class SFServiceMock
{
  // URI where this mock can be reached: to be referenced in tests that use it!
  public static final String URI = Ivy.var().variable("salesforce-connector.auth.subdomain").value() + ".my.salesforce.com/services/data/v58.0/";
  
  public SFServiceMock() {}
  
  @GET
  @Path("query")
  @Produces(MediaType.APPLICATION_JSON)
  public String getLists(@QueryParam("q") String q)
  {
    return load("json/oppLists.json");
  }
  
  private static String load(String path)
  {
    try(InputStream is = SFServiceMock.class.getResourceAsStream(path))
    {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    }
    catch (IOException ex)
    {
      throw new RuntimeException("Failed to read resource: "+path);
    }
  }

}
