package br.cr.wcaquino.testes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.rest.core.BaseTests;



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
			.log().all()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
			
		;
	}
	
	@Test
	public void devoInserirTransaçõesComSucesso() {
		
		Movimentacoes mov = new Movimentacoes();
		
		mov.setData_transacao("10/07/2000");
		mov.setData_pagamento("13/07/2010");
		mov.setDescricao("movimentando conta");
		mov.setConta_id(1245782);
		mov.setEnvolvido("Fabio Massalino");
		mov.setStatus(true);
		mov.setValor(100f);
		mov.setTipo("REC");
		
		given()
			.header("Authorization", "JWT " + token)
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			//.log().all()
			.statusCode(201)
			
		;
	}
	@Test
	public void deveValidarCamposObrigatoriosDaMovimentacao() {
		
		
		given()
			.header("Authorization", "JWT " + token)
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("$.findAll{it <= 8}.size()", is(8))
			.body("msg", hasItems("Data da Movimentação é obrigatório",
								"Data do pagamento é obrigatório",
								"Descrição é obrigatório",
								"Interessado é obrigatório",
								"Valor é obrigatório",
								"Valor deve ser um número",
								"Conta é obrigatório",
								"Situação é obrigatório"))
			
		;
	}
	@Test
	public void naoDevoCadastrarMovimentacaoFutura() {
		Movimentacoes mov = new Movimentacoes();
		
		mov.setData_transacao("16/07/2022");
		mov.setData_pagamento("19/07/2022");
		mov.setDescricao("movimentando conta");
		mov.setConta_id(1245782);
		mov.setEnvolvido("Jonas");
		mov.setStatus(true);
		mov.setValor(100f);
		mov.setTipo("REC");
		
		
		given()
			.header("Authentication", "JWT " + token)
			.body(mov)
		.when()
			.post("/trasacoes")
		.then()
			.log().all()
			.statusCode(400)
		
		;
	}
}
