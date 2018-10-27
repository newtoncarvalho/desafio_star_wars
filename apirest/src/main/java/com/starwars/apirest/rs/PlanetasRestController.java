package com.starwars.apirest.rs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mongodb.client.result.DeleteResult;
import com.starwars.apirest.dominio.Planeta;
import com.starwars.apirest.persistencia.IRepositorioPlaneta;
import com.starwars.apirest.util.TratamentoErro;

@RestController
@RequestMapping("/api-rest-starwars")
public class PlanetasRestController {
	
	@Autowired
	private IRepositorioPlaneta repositorioPlaneta;
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public ResponseEntity<List<Planeta>> listarTodosPlanetas() {
//		List<Planeta> todos = this.repositorioPlaneta.listTodos();
//		if (todos.isEmpty()) {
//			// Indicar status de conteudo vazio, ou not found => HttpStatus.NOT_FOUND
//			return new ResponseEntity<List<Planeta>>(todos, HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<Planeta>>(todos, HttpStatus.OK);		
//	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/planetas", method = RequestMethod.GET)
	public ResponseEntity<List<Planeta>> listarPlanetasPorNome(@RequestParam(name="nome", required=false) String nomePlaneta) {
		
		// Se parametro "nome" nao informado na uri (/planetas?nome=xpto), lista todos os planetas
		boolean paramNomePlanetaInformado = !StringUtils.isEmpty(nomePlaneta); 
		
		List<Planeta> planetas = paramNomePlanetaInformado ? this.repositorioPlaneta.findAproxPorNome(nomePlaneta) :
				this.repositorioPlaneta.listTodos();
		
		if (planetas.isEmpty()) {
			String msg = paramNomePlanetaInformado ? 
					"Nenhum planeta encontrado para o nome [" + nomePlaneta +  "]" :
					"Nenhum planeta cadastrado";
			TratamentoErro tratamentoErro = new TratamentoErro();
			tratamentoErro.setMensagemErro(msg);
			return new ResponseEntity(tratamentoErro, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Planeta>>(planetas, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/planetas/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPlanetaPorId(@PathVariable("id") long id) {
		Planeta p = repositorioPlaneta.findPorID(id);	
		
		if (p == null) {
			String msg = "Nenhum planeta encontrado para o id [" + id +  "]";
			TratamentoErro tratamentoErro = new TratamentoErro();
			tratamentoErro.setMensagemErro(msg);
			return new ResponseEntity<>(tratamentoErro, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Planeta>(p, HttpStatus.OK);
	}

	@RequestMapping(value="/planetas", method = RequestMethod.POST)
	public ResponseEntity<?> inserirPlaneta(@RequestBody Planeta planeta, UriComponentsBuilder ucBuilder) {
		
		Planeta p = this.repositorioPlaneta.findUnicoPorNome(planeta.getNome());
		if (p != null) {
			String msg = "Ja existe planeta com o nome [" + planeta.getNome() +  "]";
			TratamentoErro tratamentoErro = new TratamentoErro();
			tratamentoErro.setMensagemErro(msg);
			return new ResponseEntity<>(tratamentoErro, HttpStatus.CONFLICT);
		}
		
		this.repositorioPlaneta.persiste(planeta);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ucBuilder.path("/api-rest-starwars/planetas/{id}").buildAndExpand(planeta.getId()).toUri());
        return new ResponseEntity<String>(httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/planetas/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarPlaneta(@PathVariable long id) {
		
		DeleteResult deleteResult = repositorioPlaneta.deletePorID(id);
		if (deleteResult.getDeletedCount() == 0L) {
			String msg = "Nenhum planeta encontrado para o id [" + id +  "]";
			TratamentoErro tratamentoErro = new TratamentoErro();
			tratamentoErro.setMensagemErro(msg);
			return new ResponseEntity<>(tratamentoErro, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Planeta>(HttpStatus.NO_CONTENT);		
	}
}