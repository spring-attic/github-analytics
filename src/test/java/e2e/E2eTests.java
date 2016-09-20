package e2e;

import java.lang.invoke.MethodHandles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.github.GithubData;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.awaitility.Awaitility.await;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = E2eTests.class,
		webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableAutoConfiguration
public class E2eTests {

	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

	@Value("${application.url}") String applicationUrl;

	RestTemplate restTemplate = new RestTemplate();

	@Test
	@SuppressWarnings("Duplicates")
	public void shouldStoreAMessageWhenGithubDataWasReceivedFromServiceDiscovery() {
		final Integer countOfEntries = countGithubData();
		log.info("Initial count is [" + countOfEntries + "]");

		ResponseEntity<GithubData> response = callData();
		then(response.getStatusCode().is2xxSuccessful()).isTrue();
		then(response.getBody()).isNotNull();

		log.info("Awaiting proper count of github data");
		await().until(() -> countGithubData() > countOfEntries);
	}

	private ResponseEntity<GithubData> callData() {
		return this.restTemplate.getForEntity("http://" +
				this.applicationUrl + "/data", GithubData.class);
	}

	private Integer countGithubData() {
		Integer response = this.restTemplate
				.getForObject("http://" + this.applicationUrl + "/count", Integer.class);
		log.info("Received response [" + response + "]");
		return response;
	}
}
