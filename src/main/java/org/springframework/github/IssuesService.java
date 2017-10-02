package org.springframework.github;

import java.util.ArrayList;
import java.util.List;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Marcin Grzejszczak
 */
@Service
class IssuesService {
	private static final Logger log = LoggerFactory.getLogger(IssuesService.class);

	private final IssuesRepository repository;

	IssuesService(IssuesRepository repository, MeterRegistry meterRegistry) {
		this.repository = repository;
		meterRegistry.gauge("issues", this, IssuesService::count);
	}

	void save(String user, String repo) {
		log.info("Saving user [{}], and repo [{}]", user, repo);
		this.repository.save(new Issues(user, repo));
	}

	List<IssueDto> allIssues() {
		List<IssueDto> dtos = new ArrayList<>();
		this.repository.findAll().forEach(i -> dtos.add(new IssueDto(i.getUsername(), i.getRepository())));
		return dtos;
	}

	long numberOfIssues() {
		return count();
	}

	private long count() {
		return this.repository.count();
	}

	void deleteAll() {
		log.info("Deleting all issues");
		this.repository.deleteAll();
	}

}

