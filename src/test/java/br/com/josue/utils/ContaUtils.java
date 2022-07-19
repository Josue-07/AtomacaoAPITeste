package br.com.josue.utils;

import io.restassured.RestAssured;

public class ContaUtils {

	public static Integer getIdDaContaPeloNome(String nomeConta) {

		return RestAssured.get("/contas?nome=" + nomeConta).then().extract().path("id[0]");
	}

	public static Integer getIdDaMovimentacaoPeloNome(String nomeContaMovimentacao) {

		return RestAssured.get("/transacoes?nome=" + nomeContaMovimentacao).then().extract().path("id[0]");
	}
}
