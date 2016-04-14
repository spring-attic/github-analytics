package org.springframework.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.app.fieldvaluecounter.sink.FieldValueCounterSinkStoreConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FieldValueCounterSinkStoreConfiguration.class)
public class AnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}
