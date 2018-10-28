package com.starwars.apirest.dominio;

public class Filme {
	private String nome;
	
	public Filme() {}
	
	public Filme(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
