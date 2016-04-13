package org.springframework.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.fieldvaluecounter.sink.FieldValueCounterSinkStoreConfiguration;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableBinding(Sink.class)
@Import(FieldValueCounterSinkStoreConfiguration.class)
public class AnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsApplication.class, args);
	}
}
