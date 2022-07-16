package br.cr.wcaquino.testes;

public class Movimentacoes {

	private int id;
	private String envolvido;
	private int usuario_id;
	private String tipo;
	private String descricao;
	private int conta_id;
	private String situacao;
	private double valor;
	private String data_pagamento;
	private String data_transacao;
	private boolean status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnvolvido() {
		return envolvido;
	}
	public void setEnvolvido(String envolvido) {
		this.envolvido = envolvido;
	}
	public int getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getConta_id() {
		return conta_id;
	}
	public void setConta_id(int conta_id) {
		this.conta_id = conta_id;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getData_pagamento() {
		return data_pagamento;
	}
	public void setData_pagamento(String data_pagamento) {
		this.data_pagamento = data_pagamento;
	}
	public String getData_transacao() {
		return data_transacao;
	}
	public void setData_transacao(String data_transacao) {
		this.data_transacao = data_transacao;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
