package rest

org.springframework.cloud.contract.spec.Contract.make {
	request {
		method 'POST'
		url '/data'
		body([
		        username: 'smithapitla',
				repository: 'spring-cloud/spring-cloud-netflix',
				type: 'issue',
				action: 'created'
		])
	}
	response {
		status 200
	}
}