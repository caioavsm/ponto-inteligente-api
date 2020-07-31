package com.estudos.pontointeligente.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.response.Response;
import com.estudos.pontointeligente.services.FuncionarioService;

public class FuncionarioController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping(value = "/listar/{cpf}")
	public ResponseEntity<Response<Funcionario>> listar(@PathVariable("cpf") String cpf) {
		Response<Funcionario> response = new Response<Funcionario>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf(cpf);
		if(!funcionario.isPresent()) {
			response.getErrors().add("Funcionário não encontrar!" + cpf);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(funcionario.get());
		return ResponseEntity.ok(response);
	}
}
