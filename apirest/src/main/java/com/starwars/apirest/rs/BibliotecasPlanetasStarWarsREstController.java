package com.starwars.apirest.rs;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;

@RestController
@RequestMapping("/swrsapi/planetas")
public class BibliotecasPlanetasStarWarsREstController {
	
	@Autowired
	private IRepositorioPlaneta repositorioPlaneta;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Planeta> listarTodosPlanetas() {
		return repositorioPlaneta.listTodos();		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Planeta getPlanetaPorId(@PathVariable("id") ObjectId id) {
		long idLong = Long.getLong(id.toString());
		return repositorioPlaneta.findPorID(idLong);		
	}
	
	
	/*
	
	
	@RequestMapping(value = “/”, method = RequestMethod.GET)
	public List<Pets> getAllPets() {
	  return repository.findAll();
	}

	@RequestMapping(value = “/{id}”, method = RequestMethod.GET)
	public Pets getPetById(@PathVariable(“id”) ObjectId id) {
	  return repository.findBy_id(id);
	}
	
	*/
}
