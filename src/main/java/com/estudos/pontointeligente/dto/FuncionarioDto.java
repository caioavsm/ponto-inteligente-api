package com.estudos.pontointeligente.dto;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class FuncionarioDto {
	
	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 300, message = "Nome deve conter entre 3 e 300 caracteres.")
	private String nome;
	
	@NotEmpty(message = "E-mail não pode ser vazio.")
	@Length(min = 2, max = 200, message = "E-mail deve conter entre 3 e 300 caracteres.")
	@Email(message = "E-mail inválido")
	private String email;
	
	private Optional<String> senha = Optional.empty();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Optional<String> getSenha() {
		return senha;
	}
	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}

}
