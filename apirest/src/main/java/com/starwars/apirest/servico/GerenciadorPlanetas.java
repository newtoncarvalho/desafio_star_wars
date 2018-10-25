package com.starwars.apirest.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlanetaCustomizado;

@Service
public class GerenciadorPlanetas {
	
//	@Autowired
//	private IRepositorioPlanetaCustomizado repositorioPlanetas;
//	
//	public List<Planeta> getTodosPlanetas() {
//		List<Planeta> planetas = this.repositorioPlanetas.findAll();
//		return planetas;
//	}
//	
//	
//	public IRepositorioPlanetaCustomizado getRepositorioPlanetas() {
//		return repositorioPlanetas;
//	}
//	
//	public void setRepositorioPlanetas(IRepositorioPlanetaCustomizado repositorioPlanetas) {
//		this.repositorioPlanetas = repositorioPlanetas;
//	}
//	
}
