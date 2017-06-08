package rest

org.springframework.cloud.contract.spec.Contract.make {
	request {
		method GET()
		url '/issues'
	}
	response {
		status 200
		body(
				userName: 'foo',
				repository: 'spring-cloud/bar'
		)
	}
}