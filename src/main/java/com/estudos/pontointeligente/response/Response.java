package com.estudos.pontointeligente.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class Response<T> {
	private T data;
	private List<String> errors;
	private Page<T> paginacao;
	
	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public Page<T> getPaginacao() {
		return paginacao;
	}

	public void setPaginacao(Page<T> paginacao) {
		this.paginacao = paginacao;
	}
	
}
