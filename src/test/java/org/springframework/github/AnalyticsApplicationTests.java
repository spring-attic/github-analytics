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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
@AutoConfigureStubRunner(ids = {"com.example.github:github-webhook"})
@ActiveProfiles("test")
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
		GithubDatum data = new GithubDatum();
		data.setRepository("spring-framework");
		data.setUsername("rossen");
		String json = new ObjectMapper().writeValueAsString(data);
		Message<String> message =
				MessageBuilder.withPayload(json).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
		this.sink.input().send(message);
	}

	@Test
	public void testWithV1StubData() {
		int initialSize = this.githubDataListener.stats.get();

		this.stubTrigger.trigger("issue_created_v1");

		then(this.githubDataListener.counter).isNotEmpty();
		then(this.githubDataListener.stats.get()).isGreaterThan(initialSize);
	}

	@Test
	public void testWithV2StubData() {
		int initialSize = this.githubDataListener.stats.get();

		this.stubTrigger.trigger("issue_created_v2");

		then(this.githubDataListener.counter).isNotEmpty();
		then(this.githubDataListener.stats.get()).isGreaterThan(initialSize);
	}

	@Test
	public void testWithInMemoryServiceDiscovery() {
		int initialSize = this.githubDataListener.stats.get();

		GithubData data = this.githubDataListener.data();

		then(this.githubDataListener.counter).isNotEmpty();
		then(this.githubDataListener.stats.get()).isGreaterThan(initialSize);
	}

}
