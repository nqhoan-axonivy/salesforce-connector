package com.salesforce;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.axonivy.connector.salesforce.auth.OAuth2Feature;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.rest.client.RestClient;
import ch.ivyteam.ivy.rest.client.RestClients;
import ch.ivyteam.ivy.rest.client.security.CsrfHeaderFeature;

public class SFTestClient {

  public static final UUID SF_CLIENT_ID = UUID.fromString("a8bd1ef0-7283-4211-9672-3231654ad15f");
  private static final AtomicReference<RestClient> ORIGINAL = new AtomicReference<>();

  public static void resetForApp(IApplication app) {
    RestClient client = ORIGINAL.get();
    if (client != null) {
      RestClients.of(app).set(client);
      ORIGINAL.set(null);
    }
  }

  public static void mockForApp(IApplication app) {
    RestClients clients = RestClients.of(app);
    RestClient sfClient = clients.find(SF_CLIENT_ID);
    if (ORIGINAL.get() == null) {
      ORIGINAL.set(sfClient);
    }

    var sfMock = sfClient
      .toBuilder()
      .uri(SFServiceMock.URI)
      .feature(CsrfHeaderFeature.class.getName())
      .property("AUTH.baseUri", SFAuthMock.URI)
      .property("AUTH.secretKey", "1")
      .toRestClient();

    var features = new ArrayList<>(sfMock.features());
    if (!features.contains(CsrfHeaderFeature.class.getName())) {
      features.add(CsrfHeaderFeature.class.getName());
    }
    features.remove(OAuth2Feature.class.getName());
    sfMock = new RestClient(sfMock.uri(), sfMock.name(), sfMock.uniqueId(), sfMock.description(),
      features, sfMock.properties(), sfMock.metas());

    clients.set(sfMock);
  }

}