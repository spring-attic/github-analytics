/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.github;


public class GithubDatum {

	private String username;

	private String repository;

	private String type = "unknown";

	private String action = "unknown";

	public GithubDatum(String username, String repository, String type, String action) {
		this.username = username;
		this.repository = repository;
		this.type = type;
		this.action = action;
	}

	public GithubDatum() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRepository() {
		return this.repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override public String toString() {
		return "GithubDatum{" + "username='" + username + '\'' + ", repository='"
				+ repository + '\'' + ", type='" + type + '\'' + ", action='" + action
				+ '\'' + '}';
	}
}
