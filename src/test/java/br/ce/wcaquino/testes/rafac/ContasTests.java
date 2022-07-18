package br.ce.wcaquino.testes.rafac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ce.wcaquino.rest.core.BaseTests;
import io.restassured.RestAssured;


public class ContasTests extends BaseTests {

	@BeforeClass
	public static void login() {

		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("email", "josue@gmail.com");
		parametros.put("senha", "123456");

		String token = given().body(parametros).when().post("/signin").then().statusCode(200).extract().path("token");

		RestAssured.requestSpecification.header("Authorization", "JWT " + token);
		
		RestAssured.get("/reset").then().statusCode(200);

	}
	
	@Test
	public void devoIncluirUmaContaComSucesso() {
		
		Map<String, String> novaConta = new HashMap<String, String>();
		novaConta.put("nome", "Conta para alteração");

		 given()
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
		parametros.put("nome","josue 2");
		
		 given()
			.body(parametros)
			.pathParam("id", getIdDaContaPeloNome("Conta para alterar"))
		.when()
			.put("/contas/{id}")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", is("josue 2"))
		;
		
	}
	
	@Test
	public void naoDeveInserirUmaContaDeMesmoNome() {
		Map<String, String> paramentros = new HashMap<String, String>();
		
		paramentros.put("nome","Conta mesmo nome");
		
		given()
			.body(paramentros)
		.when()
			.post("/contas")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
			
		;
	}
	
	public Integer getIdDaContaPeloNome(String nomeConta) {
	
		return RestAssured.get("/contas?nome="+nomeConta).then().extract().path("id[0]");
	}
}
