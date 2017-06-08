package org.springframework.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AnalyticsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.example.github:github-webhook"},
		repositoryRoot = "${REPO_WITH_JARS:https://repo.spring.io/milestone/}")
@ActiveProfiles("test")
public class AnalyticsApplicationTests {

	@Autowired StubTrigger stubTrigger;
	@Autowired IssuesRepository repo;

	@Test
	public void should_store_a_new_issue() {
		assertThat(this.repo.count()).isEqualTo(0L);

		this.stubTrigger.trigger("issue_created_v2");

		then(this.repo.count()).isEqualTo(1L);
	}

}