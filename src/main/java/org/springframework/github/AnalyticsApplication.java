package org.springframework.github;

import java.util.Arrays;

import org.springframework.analytics.metrics.FieldValueCounterRepository;
import org.springframework.analytics.metrics.memory.InMemoryFieldValueCounterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableBinding(Sink.class)
public class AnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}

	@Bean InMemoryFieldValueCounterRepository repository() {
		return new InMemoryFieldValueCounterRepository();
	}

	@Bean GithubDataListener githubDataListener(FieldValueCounterRepository fieldValueCounterRepository,
			GithubDataListener.WebhookService webhookService) {
		return new GithubDataListener(fieldValueCounterRepository, webhookService);
	}

	@Bean
	GithubDataListener.WebhookService webhookService(final @LoadBalanced RestTemplate restTemplate) {
		return () -> restTemplate.getForObject("http://github-webhook/", GithubData.class);
	}

	/**
	 * Since an app in Cloud Foundry can't run on multiple ports, for HTTP we need to do
	 * a poor man's version and stub out any communication if we want to just check if the
	 * application is properly packaged
	 */
	@Bean
	@Profile("smoke")
	@Primary
	GithubDataListener.WebhookService integrationWebhookService() {
		return () -> {
			GithubData githubData = new GithubData();
			githubData.setData(Arrays.asList(
					new GithubDatum("dsyer", "spring-cloud-samples", "hook", "updated"),
					new GithubDatum("smithapitla", "spring-cloud/spring-cloud-netflix", "issue", "created")));
			return githubData;
		};
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
