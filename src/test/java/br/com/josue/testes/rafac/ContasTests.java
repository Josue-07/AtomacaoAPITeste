package br.com.josue.testes.rafac;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.com.josue.rest.core.BaseTests;
import br.com.josue.utils.ContaUtils;



public class ContasTests extends BaseTests {
	
	@Test
	public void devoIncluirUmaContaComSucesso() {
		
		Map<String, String> novaConta = new HashMap<String, String>();
		novaConta.put("nome", "Conta Inserida");

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
		parametros.put("nome","Conta alterada");
		
		Integer CONTA_ID = ContaUtils.getIdDaContaPeloNome("Conta para alterar");
		
		 given()
			.body(parametros)
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			//.log().all()
			.statusCode(200)
			.body("nome", is("Conta alterada"))
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
			//.log().all()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
			
		;
	}
	
}
