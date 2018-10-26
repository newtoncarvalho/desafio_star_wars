package com.starwars.apirest.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
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

	@Override
	public Planeta findPorID(long id) {
		Criteria criteria = new Criteria("id").is(id);
		Query queryPorId = new Query(criteria);		
		Planeta planeta = this.mongoTemplate.findOne(queryPorId, Planeta.class);		
		return planeta;
	}
	
	@Override
	public DeleteResult deletePorID(long id) {
		Criteria criteria = new Criteria("id").is(id);
		Query queryPorId = new Query(criteria);
		return this.mongoTemplate.remove(queryPorId, Planeta.class);
	}

	@Override
	public DeleteResult deletePorNome(String nome) {
		Criteria criteria = new Criteria("nome").is(nome);
		Query queryPorId = new Query(criteria);
		return this.mongoTemplate.remove(queryPorId, Planeta.class);
	}
}
