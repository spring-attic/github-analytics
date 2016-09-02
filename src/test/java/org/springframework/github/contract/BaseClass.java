package org.springframework.github.contract;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.github.AnalyticsApplication;
import org.springframework.github.GithubDataListener;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class)
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

}
