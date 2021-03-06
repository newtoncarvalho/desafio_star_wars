package com.starwars.apirest.persistencia;

import java.util.List;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;

public interface IRepositorioPlaneta {
	Planeta findUnicoPorNome(String nome);
	
	Planeta findPorID(long id);

	List<Planeta> findPorNome(String nome);

	List<Planeta> findAproxPorNome(String nome);

	DeleteResult deletePorID(long id);
	
	DeleteResult deletePorNome(String nome);

	Planeta insert(String nome, String clima, String terreno);

	Planeta persiste(Planeta planeta);

	long count();

	DeleteResult removeTodos();

	List<Planeta> listTodos();
}
