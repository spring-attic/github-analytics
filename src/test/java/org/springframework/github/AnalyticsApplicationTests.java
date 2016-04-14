package org.springframework.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AnalyticsApplication.class)
public class AnalyticsApplicationTests {

	@Autowired
	private Sink sink;


	@Test
	public void contextLoads() throws JsonProcessingException {

		GithubData data = new GithubData();
		data.setRepository("spring-framework");
		data.setUsername("rossen");
		String json = new ObjectMapper().writeValueAsString(data);
		Message<String> message =
				MessageBuilder.withPayload(json).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();
		this.sink.input().send(message);

	}



}
