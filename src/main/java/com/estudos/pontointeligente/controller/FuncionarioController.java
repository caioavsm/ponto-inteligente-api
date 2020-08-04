package com.estudos.pontointeligente.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.pontointeligente.dto.FuncionarioDto;
import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.response.Response;
import com.estudos.pontointeligente.services.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) {
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);

		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Funcionário não cadastrada!"));
		}

		this.atualizarDadosFuncionario(funcionarioDto, funcionario.get(), result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.funcionarioService.salvar(funcionario.get());

		response.setData(this.converterFuncionarioParaDto(funcionario.get()));
		return ResponseEntity.ok(response);
	}

	private void atualizarDadosFuncionario(FuncionarioDto funcionarioDto, Funcionario funcionario,
			BindingResult result) {
		funcionario.setNome(funcionarioDto.getNome());

		funcionario.setEmail(funcionarioDto.getEmail());

		if (!funcionarioDto.getSenha().isPresent()) {
			funcionario.setSenha(funcionarioDto.getSenha().get());
		}
	}

	private FuncionarioDto converterFuncionarioParaDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setId(funcionario.getId());
		return funcionarioDto;
	}	

	@GetMapping(value = "/{cpf}")
	public ResponseEntity<Response<Funcionario>> consultar(@PathVariable("cpf") String cpf) {
		Response<Funcionario> response = new Response<Funcionario>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf(cpf);
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionário não encontrado!" + cpf);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(funcionario.get());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Funcionario>> consultarPorId(@PathVariable("id") Long id) {
		Response<Funcionario> response = new Response<Funcionario>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			response.getErrors().add("Funcionário não encontrado!" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(funcionario.get());
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/listar")
	public ResponseEntity<Response<Funcionario>> listar() {
		Response<Funcionario> response = new Response<Funcionario>();
		Page<Funcionario> pageFuncionario = this.funcionarioService.listar();
		
		if(pageFuncionario.isEmpty() || pageFuncionario.getNumberOfElements() == 0) {
			response.getErrors().add("Lista de funcionários vazia!");
			return ResponseEntity.badRequest().body(response);
		}
		response.setPaginacao(pageFuncionario);
		return ResponseEntity.ok(response);
	}
}
