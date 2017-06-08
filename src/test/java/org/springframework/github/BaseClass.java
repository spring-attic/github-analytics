package org.springframework.github;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;

/**
 * @author Marcin Grzejszczak
 */
public class BaseClass {

	@Before
	public void setup() {
		RestAssuredMockMvc.standaloneSetup(new IssuesController(issuesService()));
	}

	IssuesService issuesService() {
		IssuesService repo = Mockito.mock(IssuesService.class);
		given(repo.numberOfIssues()).willReturn(5L);
		given(repo.allIssues()).willReturn(issues());
		return repo;
	}

	private List<IssueDto> issues() {
		List<IssueDto> dtos = new ArrayList<>();
		dtos.add(new IssueDto("foo", "spring-cloud/bar"));
		return dtos;
	}
}
