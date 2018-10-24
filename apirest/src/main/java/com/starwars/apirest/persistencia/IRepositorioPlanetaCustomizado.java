package com.starwars.apirest.persistencia;

import java.util.List;

import com.starwars.apirest.dominio.Planeta;

public interface IRepositorioPlanetaCustomizado {
	Planeta findUnicoPorNome(String nome);

	List<Planeta> findPorNome(String nome);

	List<Planeta> findAproxPorNome(String nome);
}
