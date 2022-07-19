package br.com.josue.testes.rafac;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;



import org.junit.Test;

import br.com.josue.rest.core.BaseTests;
import br.com.josue.utils.ContaUtils;

public class SaldoTests extends BaseTests {

	@Test
	public void deveCalcularOSaldoDasContas() {
		Integer CONTA_ID = ContaUtils.getIdDaContaPeloNome("Conta para extrato");
		
		given()

		.when()
			.get("/saldo")
		.then()
			//.log().all()
			.statusCode(200)
			.body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("-220.00"))
		
		;
	}
	
	
}
