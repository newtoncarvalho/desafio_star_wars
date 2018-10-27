package com.starwars.apirest.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.starwars.apirest.dominio.Planeta;

public interface IRepositorioPlaneta extends MongoRepository<Planeta, String>, IRepositorioPlanetaCustomizado {
		
}
