package com.starwars.apirest.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.starwars.apirest.dominio.Planeta;

@Repository
public class RepositorioPlanetaCustomizado implements IRepositorioPlanetaCustomizado {
	
	@Autowired
	private MongoTemplate mongoTemplate;
		
	@Override
	public Planeta findUnicoPorNome(String nome) {
		Criteria criteria = new Criteria("nome").is(nome);
		Query queryPorNome = new Query(criteria);		
		Planeta planeta = this.mongoTemplate.findOne(queryPorNome, Planeta.class);		
		return planeta;
	}
	
	@Override
	public List<Planeta> findPorNome(String nome) {
		Criteria criteria = new Criteria("nome").is(nome);
		Query queryPorNome = new Query(criteria);		
		List<Planeta> planetas = this.mongoTemplate.find(queryPorNome, Planeta.class);
		return planetas;
	}
	
	@Override
	public List<Planeta> findAproxPorNome(String nome) {
		Criteria criteria = new Criteria("nome").regex(nome);
		Query queryPorNome = new Query(criteria);		
		List<Planeta> planetas = this.mongoTemplate.find(queryPorNome, Planeta.class);
		return planetas;
	}	
}
