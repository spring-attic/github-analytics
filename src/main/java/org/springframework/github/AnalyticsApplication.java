package org.springframework.github;

import org.springframework.analytics.metrics.FieldValueCounterRepository;
import org.springframework.analytics.metrics.memory.InMemoryFieldValueCounterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
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
			@LoadBalanced RestTemplate restTemplate) {
		return new GithubDataListener(fieldValueCounterRepository, restTemplate);
	}

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
