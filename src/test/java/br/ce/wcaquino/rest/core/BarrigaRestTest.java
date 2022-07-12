package br.ce.wcaquino.rest.core;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;



public class BarrigaRestTest extends BaseTests {
	
	private String token;
	
	@Before
	public void login() {
		
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("email", "josue@gmail.com");
		parametros.put("senha", "123456");
	
			token = given()
				.body(parametros)
			.when()
				.post("/signin")
			.then()
				.statusCode(200)
				.extract().path("token")
			;
			
	}

	@Test
	public void naoDeveAcessarSemToken() {
		given()
			
		.when()
			.get("/contas")
		.then()
			//.log().all()
			.statusCode(401)
		;
			
	}
	@Test
	public void devoIncluirUmaContaComSucesso() {
		
		Map<String, String> novaConta = new HashMap<String, String>();
		novaConta.put("nome", "Conta Seu Barriga 2");

		given()
			.header("Authorization", "JWT " + token)
			.body(novaConta)
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			
		;
	}
	@Test
	public void devoAlterarContaComSucesso() {
	
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("nome", "josue 2");
		
		 given()
			.header("Authorization", "JWT " + token)
			.body(parametros)
		.when()
			.put("/contas/1253077")
		.then()
			//.log().all()
			.statusCode(200)
			.body("nome", is("josue 2"))
			
		;
		
	}
	@Test
	public void naoDeveInserirUmaContaDeMesmoNome() {
		Map<String, String> paramentros = new HashMap<String, String>();
		
		paramentros.put("nome", "josue 2");
		
		given()
			.header("Authorization", "JWT " + token)
			.body(paramentros)
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
			
		
		;
	}
}
