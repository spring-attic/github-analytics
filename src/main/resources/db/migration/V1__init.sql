CREATE TABLE issues (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	username varchar(255) not null,
	repository varchar(255) not null
);