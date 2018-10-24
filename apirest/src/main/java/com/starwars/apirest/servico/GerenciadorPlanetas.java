package com.starwars.apirest.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;

@Service
public class GerenciadorPlanetas {
	
	@Autowired
	private IRepositorioPlaneta repositorioPlanetas;
	
	public List<Planeta> getTodosPlanetas() {
		List<Planeta> planetas = this.repositorioPlanetas.findAll();
		return planetas;
	}
	
	
	public IRepositorioPlaneta getRepositorioPlanetas() {
		return repositorioPlanetas;
	}
	
	public void setRepositorioPlanetas(IRepositorioPlaneta repositorioPlanetas) {
		this.repositorioPlanetas = repositorioPlanetas;
	}
	
}
