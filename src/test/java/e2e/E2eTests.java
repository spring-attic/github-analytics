package e2e;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
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
	@Value("${classpath:json/issue-created.json}") Resource json;
	@Value("${test.timeout:60}") Long timeout;

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void shouldStoreAMessageWhenGithubDataWasReceivedViaMessaging()
			throws IOException {
		Awaitility.await().atMost(this.timeout, TimeUnit.SECONDS).untilAsserted(() -> {
			final Integer countOfEntries = countGithubData();
			log.info("Initial count is [" + countOfEntries + "]");

			ResponseEntity<String> response = callData();
			then(response.getStatusCode().is2xxSuccessful()).isTrue();
			then(response.getBody()).isNotNull();

			log.info("Awaiting proper count of github data");
			await().until(() -> countGithubData() > countOfEntries);
		});
	}

	private ResponseEntity<String> callData() throws IOException {
		return this.restTemplate.exchange(RequestEntity
				.post(URI.create("http://" +
						this.applicationUrl.replace("github-analytics", "github-webhook")))
				.contentType(MediaType.APPLICATION_JSON)
				.body(data()), String.class);
	}

	public String data() throws IOException {
		return new String(Files.readAllBytes(this.json.getFile().toPath()));
	}

	private Integer countGithubData() {
		Integer response = this.restTemplate
				.getForObject("http://" + this.applicationUrl + "/issues/count", Integer.class);
		log.info("Received response [" + response + "]");
		return response;
	}
}
