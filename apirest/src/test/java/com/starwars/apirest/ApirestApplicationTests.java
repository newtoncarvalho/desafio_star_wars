package com.starwars.apirest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApirestApplicationTests {
	
	Planeta[] amostra = new Planeta[] {
		new Planeta("venus", "quente", "arenoso"),
		new Planeta("marte", "frio", "rochoso"),
		new Planeta("terra", "multiclimatico", "multirelevo")
	};
	
	@Autowired
	private IRepositorioPlaneta repositorio; 

	@Test
	public void contextLoads() {
		assertThat(repositorio).isNotNull();
	}
	
	@Test
	public void limparSeNecessario() {
		long total = repositorio.count();
		
		if (total > 0L)			
			repositorio.deleteAll();
		
		for (Planeta p: amostra) {
			this.repositorio.insert(p);
		}
		
	}

}
