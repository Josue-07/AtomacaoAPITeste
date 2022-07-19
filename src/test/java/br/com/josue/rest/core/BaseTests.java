package br.com.josue.rest.core;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class BaseTests implements Constantes {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = APP_BASE_URL;
		RestAssured.port = APP_PORTA;
		RestAssured.basePath = APP_BASE_PATH;
		
		RequestSpecBuilder requBuild = new RequestSpecBuilder();
		requBuild.setContentType(APP_CONTENT_TYPE);
		RestAssured.requestSpecification = requBuild.build();
		
		ResponseSpecBuilder respBuild = new ResponseSpecBuilder();
		respBuild.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
		RestAssured.responseSpecification = respBuild.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
	}
}
