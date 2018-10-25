package com.starwars.apirest.persistencia;

public interface IRepositorioSequencia {

	long getProximoValorChave(String nomeEntidade);

}
