# WEB API - Register Users

An example of a Web API to Register new users.

#### Technologies
- Gradle
- JDK 1.8
- Spring Boot
- Mockito
- Junit
- H2 (Database in memory)
- Travis-CI
- Heroku

### Installation
#### Requirements
- JDK 1.8

Executing the command below, it'll install all the project dependencies and build the package.

```
    ./gradlew build
```

### Running

```
    java -jar build/libs/gs-registration-1.0-SNAPSHOT.jar
```

### Documentation

## Local environment
```
    http://localhost:8080/swagger-ui.html
```

## Online

To access directly the online documentation, just click [here](http://website/swagger-ui.html).

```
	http://<remote_host>/swagger-ui.html
```

### Exploring the API

## Security

To guarantee that only allowed users can access the API, we added a security layer. Therefore, to get an authorization you need to authenticate before. To make simple, I decided to use just the **Basic Auth** authentication.

```
	Basic Auth: services:123456
```


## Registring a new user

To register a new user, we need to be aware of a few constraints that we have to fulfill.

Constraints:
	* username: Supports only alphanumeric characters, with no space.
	* password: The password need to have: 
		* at least, 4 characters
		* at least, 1 uppercase letter
		* at least, 1 digit
	* birthDate: Date format ISO8601
	* ssn: The social security number is not being validated, just the format: XXX-XX-XXXX
	* The user shouldn't be on the blacklist record
		* Obs: To validate if the user is not part of the blacklist, we check against his birth date and ssn.

# Endpoint & payload
	```
		[Local]
		[POST] http://localhost:8080/api/v1/register

		[Payload]
		{
			"username": "first_user",
			"password": "Test123",
			"birthDate": "2000-31-10T05:00",
			"ssn": "235-34-3456"
		} 
	```

## Blacklist

To validate the blacklist feature, the application was intialized with some records presented on it.

SSN | Birthdate
----|----------
180-16-1242 | "1980-07-01T00:00"
601-68-2719 | "2000-10-05T00:00"
574-55-1434 | "1982-03-15T00:00"
675-38-6091 | "1970-01-01T00:00"
222-34-2922 | "1995-10-30T00:00"
