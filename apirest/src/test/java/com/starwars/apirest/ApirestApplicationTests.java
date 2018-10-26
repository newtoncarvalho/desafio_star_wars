package com.starwars.apirest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;
import com.starwars.apirest.persistencia.IRepositorioPlanetaCustomizado;
import com.starwars.apirest.persistencia.IRepositorioSequencia;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApirestApplicationTests {
	
	Planeta[] amostra = new Planeta[] {
		new Planeta("venus", "quente", "arenoso"),
		new Planeta("marte", "frio", "rochoso"),
		new Planeta("terra", "multiclimatico", "multirelevo"),
		new Planeta("terra2", "multiclimatico", "multirelevo"),
		new Planeta("terra3", "multiclimatico", "multirelevo"),
		new Planeta("terra4", "multiclimatico", "multirelevo")
	};
	
	@Autowired
	private IRepositorioPlanetaCustomizado repositorioCustomizado;
	
	@Autowired
	private IRepositorioPlaneta repositorio;
	
	@Autowired
	private IRepositorioSequencia geradorSequencia;
	
	@Test
	public void contextLoads() {
		assertThat(repositorioCustomizado).isNotNull();
		assertThat(repositorio).isNotNull();
	}
	
	@Test
	public void testarCrud() {
		long total = repositorio.count();
		
		if (total > 0L)			
			repositorio.deleteAll();
		
		geradorSequencia.limpar(Planeta.class.getName());
		
		for (Planeta p: amostra) {
			p.setId(this.geradorSequencia.getProximoValorChave(p.getClass().getName()));			
			this.repositorio.insert(p);
		}
		
		Planeta pTerra = this.repositorioCustomizado.findUnicoPorNome("terra");
		assertThat(pTerra).isNotNull();
		
		List<Planeta> listaPlanetas = this.repositorioCustomizado.findPorNome("terra");
		assertThat(listaPlanetas).isNotEmpty();
		
		listaPlanetas = this.repositorioCustomizado.findAproxPorNome("terra");
		assertThat(listaPlanetas).isNotEmpty();
		
		listaPlanetas = this.repositorioCustomizado.findAproxPorNome("a");
		assertThat(listaPlanetas).isNotEmpty();
		
		Planeta p = this.repositorioCustomizado.findPorID(4);
		assertThat(p).isNotNull();
		System.out.println(p);
		
		DeleteResult result = this.repositorioCustomizado.deletePorID(p.getId());
		System.out.println(result);
		
		result = this.repositorioCustomizado.deletePorNome("marte");
		assertThat(result.getDeletedCount()).isGreaterThan(0);
		System.out.println(result);
		
		result = this.repositorioCustomizado.deletePorNome("venus");
		assertThat(result.getDeletedCount()).isGreaterThan(0);
		System.out.println(result);
		
		result = this.repositorioCustomizado.deletePorNome("plutao");
		assertThat(result.getDeletedCount()).isEqualTo(0);
		System.out.println(result);
		
		p = this.repositorioCustomizado.findPorID(10000);
		assertThat(p).isNull();
		System.out.println(p);
	}

}
