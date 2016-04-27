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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.app.metrics.FieldValueCounterWriter;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class GithubDataListener {

	@Autowired
	private FieldValueCounterWriter fieldValueCounterWriter;
	Map<String, Object> counter = new HashMap<>();


	@StreamListener(Sink.INPUT)
	public void listen(GithubData data) {
		processValue("repository", data.getRepository());
		processValue("username", data.getUsername());
		processValue("type", data.getType());
		processValue("action", data.getAction());
	}

	void clear() {
		counter.clear();
	}

	protected void processValue(String counterName, Object value) {
		if ((value instanceof Collection) || ObjectUtils.isArray(value)) {
			Collection<?> c = (value instanceof Collection) ? (Collection<?>) value
					: Arrays.asList(ObjectUtils.toObjectArray(value));
			for (Object val : c) {
				this.fieldValueCounterWriter.increment(counterName, val.toString(), 1.0);
			}
		}
		else if (value != null) {
			this.fieldValueCounterWriter.increment(counterName, value.toString(), 1.0);
		}
		counter.put(counterName, value);
	}

}
