package com.starwars.apirest.persistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.starwars.apirest.dominio.Sequencia;


@Repository
public class RepositorioSequencia implements IRepositorioSequencia{
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Override
	public long getProximoValorChave(String nomeEntidade) {
		
		Query query = new Query(Criteria.where("entidade").is(nomeEntidade));
		
		Update update = new Update().inc("nextVal", 1); 
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true).upsert(true);
		
		Sequencia sequencia = mongoOperations.findAndModify(
				query, update, options, Sequencia.class);
	       
	    return sequencia == null ? 0L : sequencia.getNextVal();
	}
	
	@Override
	public void limpar(String nomeEntidade) {
		Query query = new Query(Criteria.where("entidade").is(nomeEntidade));
		mongoOperations.findAndRemove(query, Sequencia.class);
	}
	
}
