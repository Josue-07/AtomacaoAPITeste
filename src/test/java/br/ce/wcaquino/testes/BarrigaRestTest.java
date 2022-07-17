package br.ce.wcaquino.testes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.ce.wcaquino.rest.core.BaseTests;
import br.ce.wcaquino.utils.DataUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaRestTest extends BaseTests {
	
	//private static String token;
	private static String CONTA_NAME = "Conta " + System.nanoTime();
	private static Integer CONTA_ID;
	private static Integer MOVIMENTACAO_ID;
	
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
			
			
	}

	
	@Test
	public void ct02_devoIncluirUmaContaComSucesso() {
		
		Map<String, String> novaConta = new HashMap<String, String>();
		novaConta.put("nome", CONTA_NAME);

		CONTA_ID = given()
			.body(novaConta)
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			.extract().path("id")
			
		;
	}
	@Test
	public void ct03_devoAlterarContaComSucesso() {
	
		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("nome", CONTA_NAME + " josue 2");
		
		 given()
			.body(parametros)
			.pathParam("id", CONTA_ID)
		.when()
			.put("/contas/{id}")
		.then()
			//.log().all()
			.statusCode(200)
			.body("nome", is(CONTA_NAME + " josue 2"))
			
			
		;
		
	}
	@Test
	public void ct04_naoDeveInserirUmaContaDeMesmoNome() {
		Map<String, String> paramentros = new HashMap<String, String>();
		
		paramentros.put("nome", CONTA_NAME + " josue 2");
		
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
	
	@Test
	public void ct05_devoInserirTransaçõesComSucesso() {
		
		Movimentacoes mov = getMovimentacaoConta();
		
		MOVIMENTACAO_ID = given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.log().all()
			.statusCode(201)
			.extract().path("id")
			
		;
	}
	@Test
	public void ct06_deveValidarCamposObrigatoriosDaMovimentacao() {
		
		
		given()
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
	public void ct07_naoDevoCadastrarMovimentacaoFutura() {
		Movimentacoes mov = getMovimentacaoConta();
		
		mov.setData_transacao(DataUtils.getDiferencaDias(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			.log().all()
			.statusCode(400)
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		
		;
	}
	
	@Test
	public void ct08_naoDeveRemoverContaEmMovimentacao() {
		given()
			.pathParam("id", CONTA_ID)

		.when()
			.delete("/contas/{id}")
		.then()
			.log().all()
			.statusCode(500)
			.body("constraint", equalTo("transacoes_conta_id_foreign"))
			
		
		;
	}
	
	@Test
	public void ct09_deveCalcularOSaldoDasContas() {
		given()

		.when()
			.get("/saldo")
		.then()
			.log().all()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("100.00"))
		
		;
	}
	@Test
	public void ct10_deveRemoverMovimentacao() {
		given()
			.pathParam("id", MOVIMENTACAO_ID)

		.when()
			.delete("/transacoes/{id}")
		.then()
			.log().all()
			.statusCode(204)	
		
		;
	}
	
	@Test
	public void ct11_naoDeveAcessarSemToken() {
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
	
	private Movimentacoes getMovimentacaoConta() {
		
		Movimentacoes mov = new Movimentacoes();
		
		mov.setData_transacao(DataUtils.getDiferencaDias(-1));
		mov.setData_pagamento(DataUtils.getDiferencaDias(3));
		mov.setDescricao("movimentando conta 2");
		mov.setConta_id(CONTA_ID);
		mov.setEnvolvido("Fabio Massalino");
		mov.setStatus(true);
		mov.setValor(100f);
		mov.setTipo("REC");
		
		return mov;
	}
}
