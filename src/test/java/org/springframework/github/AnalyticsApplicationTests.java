package org.springframework.github;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
@AutoConfigureStubRunner(repositoryRoot = "https://repo.spring.io/snapshot/",
		ids = {"com.example.github:github-webhook"})
public class AnalyticsApplicationTests {

	@Autowired
	private Sink sink;

	@Autowired StubTrigger stubTrigger;
	@Autowired GithubDataListener githubDataListener;

	@Before
	public void setup() {
		this.githubDataListener.clear();
	}

	@Test
	public void testWithMarshalledPojo() throws JsonProcessingException {
		GithubData data = new GithubData();
		data.setRepository("spring-framework");
		data.setUsername("rossen");
		String json = new ObjectMapper().writeValueAsString(data);
		Message<String> message =
				MessageBuilder.withPayload(json).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
		this.sink.input().send(message);
	}

	@Test
	public void testWithV1StubData() {
		this.stubTrigger.trigger("issue_created_v1");

		assertThat(this.githubDataListener.counter.isEmpty(), is(false));
	}

	@Test
	public void testWithV2StubData() {
		this.stubTrigger.trigger("issue_created_v2");

		assertThat(this.githubDataListener.counter.isEmpty(), is(false));
	}

}
