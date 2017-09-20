package rest

org.springframework.cloud.contract.spec.Contract.make {
	request {
		method POST()
		url '/issues'
		body(
				userName: 'foo',
				repository: 'spring-cloud/bar'
		)
		headers {
			contentType(applicationJson())
		}
	}
	response {
		status 200
	}
}