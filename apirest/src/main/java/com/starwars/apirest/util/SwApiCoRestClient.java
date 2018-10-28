package com.starwars.apirest.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwApiCoRestClient {
	
	private RestTemplate restTemplate = null;
	
	public SwApiCoRestClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		this.restTemplate = new RestTemplate(requestFactory);
	}

	public List<String> getReferenciasFilmes(String nomePlaneta) {
		
		String url = "https://swapi.co/api/planets/?search=" + nomePlaneta;
		
		List<String> referencias = new ArrayList<>();
		
		LinkedHashMap<?, ?> result = restTemplate.getForObject(url, LinkedHashMap.class);
		
		if (result != null) {
        	Object o = result.get("results");
        	
        	@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> colecaoMapas = (List<LinkedHashMap<String, Object>>) o;
        	
        	// key = "films"
        	if(colecaoMapas!=null) {
        		for(LinkedHashMap<String, Object> map : colecaoMapas) {        			
        			@SuppressWarnings("unchecked")
					List<String> list =	(List<String>) map.get("films");
        			if (list != null)
        				referencias.addAll(list);
        		}
        	}
        	
        }
		
		return referencias;
	}
	
	public String getFilme(String urlFilme) {
		StringBuffer sb = new StringBuffer();
		LinkedHashMap<?, ?> result = restTemplate.getForObject(urlFilme, LinkedHashMap.class);
		
		if (result != null) {
			sb.append(result.get("title"));
        }
		
		return sb.toString();
	}
	
	public List<String> getAparicoesEmFilmes(String nomePlaneta) {
		List<String> referencias = this.getReferenciasFilmes(nomePlaneta);
		List<String> filmes = new ArrayList<>();
		
		for (String urlFilme:referencias) {
			filmes.add(this.getFilme(urlFilme));
		}
		
		return filmes;
	}
	
	public static void main(String[] args) {
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
		
		String[] nomesPlanetas = {
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
		
		SwApiCoRestClient swApiCoRestClient = new SwApiCoRestClient();
		
		for (String nome:nomesPlanetas) {
			List<String> refs = swApiCoRestClient.getReferenciasFilmes(nome);
			
			System.out.println(nome);
			for (String r:refs) {
				System.out.print('\t');
				System.out.print(r);
				System.out.print(" - ");
				System.out.print(swApiCoRestClient.getFilme(r));
			}
		}
		
	}
	
}
