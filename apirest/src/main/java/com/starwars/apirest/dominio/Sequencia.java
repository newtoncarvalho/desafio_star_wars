package com.starwars.apirest.dominio;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="sequences")
public class Sequencia {
	
	@Id
	private Object id;
	
	@Indexed(unique=true)
	private String entidade;
	
	private long nextVal;
	
	public Sequencia() {}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public long getNextVal() {
		return nextVal;
	}

	public void setNextVal(long nextVal) {
		this.nextVal = nextVal;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("entidade: ").append(entidade);
		sb.append("nextVal: ").append(nextVal);
		return sb.toString();
	}

}
