package org.springframework.github;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

import org.springframework.context.annotation.Configuration;

/**
 * @author Marcin Grzejszczak
 */
@Configuration
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
class MetricConfiguration {

}