/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.github;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "issues")
class Issues {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private String repository;

	Issues(String username, String repository) {
		this.username = username;
		this.repository = repository;
	}

	Issues() {
	}

	String getUsername() {
		return this.username;
	}

	void setUsername(String username) {
		this.username = username;
	}

	String getRepository() {
		return this.repository;
	}

	void setRepository(String lastname) {
		this.repository = lastname;
	}

	@Override
	public String toString() {
		return "IssueCreation [username=" + this.username + ", repository=" + this.repository
				+ "]";
	}
}
