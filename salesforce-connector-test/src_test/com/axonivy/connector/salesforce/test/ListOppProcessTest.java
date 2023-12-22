package com.axonivy.connector.salesforce.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.salesforce.SFTestClient;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.security.ISession;

@IvyProcessTest
public class ListOppProcessTest {
	@BeforeEach
	void mockService(IApplication app) {
		SFTestClient.mockForApp(app);
	}

	@Test
	public void readList(BpmClient bpmClient, ISession session) {
		bpmClient.mock()
			.element(BpmElement.pid("18C0022FCA6F8CC0-f3"))
			.withNoAction();

		ExecutionResult result = bpmClient.start()
				.process("TestGetAllOpps/start.ivp")
				.as()
				.session(session)
				.execute();
		assertThat(result.http().redirectLocation()).isNotEmpty();
	}
}
