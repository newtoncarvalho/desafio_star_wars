package com.starwars.apirest.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;

@Repository
public class RepositorioPlaneta implements IRepositorioPlaneta {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private IRepositorioSequencia repositorioSequencia;
	
	private String nomeColecao = null;
	
	private Collation casoInsensitivo = null;
	
	public RepositorioPlaneta() {
		Document anotacao = Planeta.class.getAnnotation(Document.class);
		if (anotacao == null)
			throw new IllegalStateException("Classe '" + Planeta.class.getName() + "' nao rotulada com anotacao '" + Document.class.getName() + "'");
		this.nomeColecao = anotacao.collection();
		
		this.casoInsensitivo = Collation.of("en").strength(Collation.ComparisonLevel.secondary());
	}
		
	@Override
	public Planeta findUnicoPorNome(String nome) {
		Criteria criteria = new Criteria("nome").is(nome);
		Query queryPorNome = new Query(criteria);
		
		// Forcando query caso-insensitivo
		queryPorNome.collation(this.casoInsensitivo);
		
		Planeta planeta = this.mongoTemplate.findOne(queryPorNome, Planeta.class);		
		return planeta;
	}
	
	@Override
	public List<Planeta> findPorNome(String nome) {
		Criteria criteria = new Criteria("nome").is(nome);
		Query queryPorNome = new Query(criteria);
		
		// Forcando query caso-insensitivo
		queryPorNome.collation(this.casoInsensitivo);
		
		List<Planeta> planetas = this.mongoTemplate.find(queryPorNome, Planeta.class);
		return planetas;
	}
	
	@Override
	public List<Planeta> findAproxPorNome(String nome) {
		Criteria criteria = new Criteria("nome").regex(nome, "i"); // "i": Insensitivo ao caso
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
	public List<Planeta> listTodos() {
		Query query = new Query();
		List<Planeta> planetas = this.mongoTemplate.find(query, Planeta.class);
		return planetas;
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
	
	@Override
	public Planeta insert(String nome, String clima, String terreno) {
		Planeta planeta = new Planeta(nome, clima, terreno);		
		return this.persiste(planeta);
	}
	
	@Override
	public Planeta persiste(Planeta planeta) {
		if (planeta == null)
			throw new IllegalArgumentException("Planeta nao informado");
		
		if (planeta.getId() == 0)
			planeta.setId(this.repositorioSequencia.getProximoValorChave(Planeta.class.getName()));
		
		this.mongoTemplate.save(planeta);
		return planeta;		
	}
	
	@Override
	public long count() {
		return this.mongoTemplate.getCollection(this.nomeColecao).count();		
	}
	
	@Override
	public DeleteResult removeTodos() {
		return this.mongoTemplate.remove(new Query(), this.nomeColecao);
	}
}
