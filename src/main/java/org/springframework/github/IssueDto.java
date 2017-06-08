package org.springframework.github;

class IssueDto {
	private String userName;
	private String repository;

	IssueDto(String userName, String repository) {
		this.userName = userName;
		this.repository = repository;
	}

	IssueDto() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}
}
