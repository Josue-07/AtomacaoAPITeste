package br.com.josue.testes.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.josue.rest.core.BaseTests;
import br.com.josue.testes.rafac.*;
import io.restassured.RestAssured;

@RunWith(Suite.class)
@SuiteClasses({
		ContasTests.class,
		MovimentacaoTests.class,
		SaldoTests.class,
		AuthTests.class	
})
public class SuiteRunner extends BaseTests {


	@BeforeClass
	public static void login() {
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("email", "josue@gmail.com");
		parametros.put("senha", "123456");
	
			 String token = given()
				.body(parametros)
			.when()
				.post("/signin")
			.then()
				.statusCode(200)
				.extract().path("token")
				;
				
		RestAssured.requestSpecification.header("Authorization", "JWT " + token);
		
		RestAssured.get("/reset").then().statusCode(200);
			
			
	}
}
