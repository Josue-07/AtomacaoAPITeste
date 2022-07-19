package br.com.josue.testes.rafac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;



import org.junit.Test;

import br.com.josue.rest.core.BaseTests;
import br.com.josue.testes.Movimentacoes;
import br.com.josue.utils.ContaUtils;
import br.com.josue.utils.DataUtils;

public class MovimentacaoTests extends BaseTests {
	
	@Test
	public void devoInserirTransaçõesComSucesso() {
		
		Movimentacoes mov = getMovimentacaoConta();
		
		given()
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
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			//.log().all()
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
		Movimentacoes mov = getMovimentacaoConta();
		
		mov.setData_transacao(DataUtils.getDiferencaDias(2));
		
		given()
			.body(mov)
		.when()
			.post("/transacoes")
		.then()
			//.log().all()
			.statusCode(400)
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		
		;
	}
	
	@Test
	public void DeveRemoverContaEmMovimentacao() {
		Integer CONTA_ID = ContaUtils.getIdDaContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id", CONTA_ID)

		.when()
			.delete("/contas/{id}")
		.then()
			//.log().all()
			.statusCode(500)
			.body("constraint", equalTo("transacoes_conta_id_foreign"))
			
		
		;
	}
	
	@Test
	public void deveRemoverMovimentacao() {
		given()
			.pathParam("id", ContaUtils.getIdDaMovimentacaoPeloNome("Conta alterada"))

		.when()
			.delete("/transacoes/{id}")
		.then()
			//.log().all()
			.statusCode(204)	
		
		;
	}
	
	private Movimentacoes getMovimentacaoConta() {
		
		Movimentacoes mov = new Movimentacoes();
		
		mov.setData_transacao(DataUtils.getDiferencaDias(-1));
		mov.setData_pagamento(DataUtils.getDiferencaDias(3));
		mov.setDescricao("movimentando conta 2");
		mov.setConta_id(ContaUtils.getIdDaContaPeloNome("Conta para movimentacoes"));
		mov.setEnvolvido("Fabio Massalino");
		mov.setStatus(true);
		mov.setValor(100f);
		mov.setTipo("REC");
		
		return mov;
	}
	

}
