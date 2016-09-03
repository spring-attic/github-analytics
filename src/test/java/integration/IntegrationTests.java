package integration;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;
import static org.awaitility.Awaitility.await;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTests.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
public class IntegrationTests {

	@Value("${stubrunner.url}") String stubRunnerUrl;
	@Value("${application.url}") String applicationUrl;

	TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Test
	public void shouldStoreAMessageWhenGithubDataWasReceived() {
		final Integer countOfEntries = countGithubData();
		Map response = triggerMessage();
		then(response).isNotEmpty();

		await().until(() -> countGithubData() > countOfEntries);
	}

	private Map triggerMessage() {
		return this.testRestTemplate.getForObject("http://" +
				this.stubRunnerUrl + "/triggers/hook_created_v2", Map.class);
	}

	private Integer countGithubData() {
		return this.testRestTemplate
				.getForObject("http://" + this.applicationUrl + "/count", Integer.class);
	}
}
