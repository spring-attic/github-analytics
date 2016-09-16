package org.springframework.github.contract;

import java.util.Arrays;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.github.AnalyticsApplication;
import org.springframework.github.GithubData;
import org.springframework.github.GithubDataListener;
import org.springframework.github.GithubDatum;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BaseClass.Config.class, AnalyticsApplication.class })
@AutoConfigureMessageVerifier
public class BaseClass {

	@Autowired GithubDataListener githubDataListener;

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(githubDataListener);
		if (this.githubDataListener.count() < 5) {
			githubDataListener.processValue("foo1", 1);
			githubDataListener.processValue("foo2", 2);
			githubDataListener.processValue("foo3", 3);
			githubDataListener.processValue("foo4", 4);
			githubDataListener.processValue("foo5", 5);
		}
	}

	@Configuration
	static class Config {
		@Bean @Primary GithubDataListener.WebhookService testWebhook() {
			return () -> {
				GithubData githubData = new GithubData();
				githubData.setData(Arrays.asList(
						new GithubDatum("dsyer", "spring-cloud-samples", "hook", "updated"),
						new GithubDatum("smithapitla", "spring-cloud/spring-cloud-netflix", "issue", "created")));
				return githubData;
			};
		}
	}
}
