package com.starwars.apirest.dominio;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;
//import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="planetas")
public class Planeta {
	
	@Id
	private long id;
	
	@Indexed(unique = true)
	private String nome;
	
	@NotBlank
	private String clima;
	@NotBlank
	private String terreno;
	
	private List<String> filmes = new ArrayList<>();
//	private String[] filmes = {};
	
	public Planeta(String nome, String clima, String terreno) {
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getClima() {
		return clima;
	}
	public void setClima(String clima) {
		this.clima = clima;
	}
	public String getTerreno() {
		return terreno;
	}
	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<String> getFilmes() {
		return filmes;
	}
	public void setFilmes(List<String> aparicao) {
		this.filmes = aparicao;
	}
	
//	public String[] getFilmes() {
//		return filmes;
//	}
//	public void setFilmes(String[] filmes) {
//		this.filmes = filmes;
//	}
	
	
	
	@Override
	public String toString() {
		return this.nome;
	}
}
