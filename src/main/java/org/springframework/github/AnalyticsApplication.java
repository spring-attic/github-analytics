package org.springframework.github;

import org.springframework.analytics.metrics.memory.InMemoryFieldValueCounterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBinding(Sink.class)
public class AnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}

	@Bean InMemoryFieldValueCounterRepository repository() {
		return new InMemoryFieldValueCounterRepository();
	}
}
