package com.starwars.apirest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;
import com.starwars.apirest.persistencia.IRepositorioSequencia;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApirestApplicationTests {
	
	/**
	 * Yavin IV
	 * Hoth
	 * Dagobah
	 * Bespin
	 * Endor
	 * Naboo
	 * Kamino
	 * Geonosis
	 * Utapau
	 * Kashyyyk
	 */
	
	String[] nomesPlanetasDoFilme = {
			"terra",
			"yavin IV",
			"hoth",
			"dagobah",
			"bespin",
			"endor",
			"naboo",
			"kamino",
			"geonosis",
			"utapau",
			"kashyyyk",	
			"mars"
	};
	
	Planeta[] amostra = new Planeta[] {
		new Planeta("venus", "quente", "arenoso"),
		new Planeta("marte", "frio", "rochoso"),
		new Planeta("terra", "multiclimatico", "multirelevo"),
		new Planeta("terra2", "multiclimatico", "multirelevo"),
		new Planeta("terra3", "multiclimatico", "multirelevo"),
		new Planeta("terra4", "multiclimatico", "multirelevo"),
		new Planeta("terra5", "multiclimatico", "multirelevo"),
		new Planeta("terra8", "multiclimatico", "multirelevo"),
		new Planeta("terra9", "multiclimatico", "multirelevo")
	};
	
	@Autowired
	private IRepositorioPlaneta repositoPlanetas;

	@Autowired
	private IRepositorioSequencia geradorSequencia;
	
	@Test
	public void contextLoads() {
		assertThat(repositoPlanetas).isNotNull();
	}
	
	@Test
	public void testarCrud() {
		
		this.geradorSequencia.limpar(Planeta.class.getName());
		
		long total = this.repositoPlanetas.count();
		
		if (total > 0L)			
			repositoPlanetas.removeTodos();
		
		for (Planeta p: amostra) {
			this.repositoPlanetas.persiste(p);
		}
		
		Planeta pTerra = this.repositoPlanetas.findUnicoPorNome("TERRA");
		assertThat(pTerra).isNotNull();
		
		List<Planeta> listaPlanetas = this.repositoPlanetas.findPorNome("TerrA");
		assertThat(listaPlanetas).isNotEmpty();
		
		listaPlanetas = this.repositoPlanetas.findAproxPorNome("tERRa");
		assertThat(listaPlanetas).isNotEmpty();
		
		listaPlanetas = this.repositoPlanetas.findAproxPorNome("RR");
		assertThat(listaPlanetas).isNotEmpty();
		
		Planeta p = this.repositoPlanetas.findPorID(4);
		assertThat(p).isNotNull();
		System.out.println(p);
		
		DeleteResult result = this.repositoPlanetas.deletePorID(p.getId());
		System.out.println(result);
		
		result = this.repositoPlanetas.deletePorNome("marte");
		assertThat(result.getDeletedCount()).isGreaterThan(0);
		System.out.println(result);
		
		result = this.repositoPlanetas.deletePorNome("venus");
		assertThat(result.getDeletedCount()).isGreaterThan(0);
		System.out.println(result);
		
		result = this.repositoPlanetas.deletePorNome("plutao");
		assertThat(result.getDeletedCount()).isEqualTo(0);
		System.out.println(result);
		
		p = this.repositoPlanetas.findPorID(10000);
		assertThat(p).isNull();
		System.out.println(p);
		
		p = new Planeta(null, null, null);
		this.repositoPlanetas.persiste(p);
		
		Exception e = null;
		try {
			p = new Planeta(null, null, null);
			this.repositoPlanetas.persiste(p);
		}
		catch (Exception exception) {
			e = exception;
		}
		assertThat(e).isNotNull().isInstanceOf(DuplicateKeyException.class);
		
		List<Planeta> todos = this.repositoPlanetas.listTodos();
		assertThat(todos).isNotEmpty();
		
		
		Planeta p2 = this.repositoPlanetas.findPorID(3);
		List<String> filmes = new ArrayList<>();
		filmes.add("teste");
		filmes.add("teste-2");
		filmes.add("teste-3");
		filmes.add("teste-2");
		p2.setFilmes(filmes);
		
//		p2.setFilmes(new String[] {"teste", "teste2", "teste4"});
		
		this.repositoPlanetas.persiste(p2);
	}
	

}
