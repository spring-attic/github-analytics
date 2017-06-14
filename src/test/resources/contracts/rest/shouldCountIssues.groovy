package rest

org.springframework.cloud.contract.spec.Contract.make {
	request {
		method GET()
		url '/issues/count'
	}
	response {
		status 200
		body 5
	}
}