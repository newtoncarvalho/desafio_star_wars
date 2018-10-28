package com.starwars.apirest.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;
import com.starwars.apirest.util.SwApiCoRestClient;

@Service
public class GerenciadorPlanetas {
	
	@Autowired
	private IRepositorioPlaneta repositorioPlaneta;
	
	@Autowired
	private SwApiCoRestClient swApiCoRestClient;
	
	public List<Planeta> buscaPlanetasPorNome(String nomePlaneta) {
		// Se parametro "nome" nao informado na uri (/planetas?nome=xpto), lista todos os planetas
		boolean paramNomePlanetaInformado = !StringUtils.isEmpty(nomePlaneta); 
		
		List<Planeta> planetas = paramNomePlanetaInformado ? this.repositorioPlaneta.findAproxPorNome(nomePlaneta) :
				this.repositorioPlaneta.listTodos();
		
		return planetas;
	}
	
	public Planeta buscaPlanetaPorId(long id) {
		Planeta p = repositorioPlaneta.findPorID(id);
		return p;
	}
	
	public Planeta buscaPlanetaPorNome(String nome) {
		Planeta p = this.repositorioPlaneta.findUnicoPorNome(nome);
		return p;
	}
	
	public Planeta persiste(Planeta planeta) {
		planeta.setFilmes(this.swApiCoRestClient.getAparicoesEmFilmes(planeta.getNome()));
		return this.repositorioPlaneta.persiste(planeta);
	}
	
	public long deletePorId(long id) {
		DeleteResult d = this.repositorioPlaneta.deletePorID(id);
		return d.getDeletedCount();
	}
	
	
//	
//	public List<Planeta> getTodosPlanetas() {
//		List<Planeta> planetas = this.repositorioPlanetas.findAll();
//		return planetas;
//	}
//	
//	
//	public IRepositorioPlaneta getRepositorioPlanetas() {
//		return repositorioPlanetas;
//	}
//	
//	public void setRepositorioPlanetas(IRepositorioPlaneta repositorioPlanetas) {
//		this.repositorioPlanetas = repositorioPlanetas;
//	}
//	
}
