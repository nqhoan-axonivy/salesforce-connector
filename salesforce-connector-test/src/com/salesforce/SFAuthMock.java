package com.salesforce;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import ch.ivyteam.ivy.environment.Ivy;
import io.swagger.v3.oas.annotations.Hidden;

@PermitAll
@Hidden
public class SFAuthMock
{
  // URI where this mock can be reached: to be referenced in tests that use it!
  public static final String URI = Ivy.var().variable("salesforce-connector.auth.subdomain").value() + ".my.salesforce.com/services/data/v58.0/";

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("authorize")
  public Response auth()
  {
    return Response.status(301)
            .build();
  }

  @POST
  @Path("token")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response token()
  {
    return Response.status(200)
      .entity(load("json/accessToken.json"))
      .build();
  }

  private static String load(String path)
  {
    try(InputStream is = SFAuthMock.class.getResourceAsStream(path))
    {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    }
    catch (IOException ex)
    {
      throw new RuntimeException("Failed to read resource: "+path);
    }
  }
}
