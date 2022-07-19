package br.com.josue.testes.rafac;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.josue.rest.core.BaseTests;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTests extends BaseTests {
	
	@Test
	public void naoDeveAcessarSemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
			
		.when()
			.get("/contas")
		.then()
			//.log().all()
			.statusCode(401)
		;
			
	}
}
