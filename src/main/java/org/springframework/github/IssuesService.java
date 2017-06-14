package org.springframework.github;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

/**
 * @author Marcin Grzejszczak
 */
@Service
class IssuesService {
	private static final Logger log = LoggerFactory.getLogger(IssuesService.class);

	private final IssuesRepository repository;
	private final GaugeService gaugeService;

	IssuesService(IssuesRepository repository, GaugeService gaugeService) {
		this.repository = repository;
		this.gaugeService = gaugeService;
	}

	void save(String user, String repo) {
		log.info("Saving user [{}], and repo [{}]", user, repo);
		this.repository.save(new Issues(user, repo));
		submitCountMetric();
	}

	private void submitCountMetric() {
		submitCountMetric(count());
	}

	private void submitCountMetric(long numbers) {
		log.info("Submitting count metric with number [{}]", numbers);
		this.gaugeService.submit("issues.count", numbers);
	}

	List<IssueDto> allIssues() {
		List<IssueDto> dtos = new ArrayList<>();
		this.repository.findAll().forEach(i -> dtos.add(new IssueDto(i.getUsername(), i.getRepository())));
		return dtos;
	}

	long numberOfIssues() {
		long count = count();
		submitCountMetric(count);
		return count;
	}

	private long count() {
		return this.repository.count();
	}

	void deleteAll() {
		log.info("Deleting all issues");
		this.repository.deleteAll();
		submitCountMetric();
	}

}

