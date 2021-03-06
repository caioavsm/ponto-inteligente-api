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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.pontointeligente.dto.EmpresaDto;
import com.estudos.pontointeligente.entities.Empresa;
import com.estudos.pontointeligente.response.Response;
import com.estudos.pontointeligente.services.EmpresaService;

@RestController
@RequestMapping("/empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {
	@Autowired
	private EmpresaService empresaService;

	@PostMapping(value = "/cadastrar")
	public ResponseEntity<Response<EmpresaDto>> cadastrar(@Valid @RequestBody EmpresaDto empresaDto,
			BindingResult result) {
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		
		validarEmpresaExistente(empresaDto, result);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Empresa empresa = this.converterDtoParaEmpresa(empresaDto);
		empresaService.salvar(empresa);
		
		response.setData(this.converterEmpresaParaDto(empresa));
		return ResponseEntity.ok(response);
	}

	private void validarEmpresaExistente(EmpresaDto empresaDto, BindingResult result) {
		
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(empresaDto.getCnpj());
		if (empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa já cadastrada!"));
		}		
	}
	
	private Empresa converterDtoParaEmpresa(EmpresaDto empresaDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(empresaDto.getCnpj());
		empresa.setRazaoSocial(empresaDto.getRazaoSocial());
		
		return empresa;
	}
	
	private EmpresaDto converterEmpresaParaDto(Empresa empresa) {
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		return empresaDto;
	}
	
	@GetMapping(value = "/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> consultar(@PathVariable("cnpj") String cnpj) {
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cnpj);
		if(!empresa.isPresent()) {
			response.getErrors().add("Empresa não encontrar!" + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converterEmpresaParaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/listar")
	public ResponseEntity<Response<Empresa>> listar() {
		Response<Empresa> responseEmpresa = new Response<Empresa>();
		Page<Empresa> pageEmpresa = this.empresaService.listar();
		
		if(pageEmpresa.isEmpty() || pageEmpresa.getNumberOfElements() == 0) {
			responseEmpresa.getErrors().add("Nenhuma Empresa cadastrada!");
			return ResponseEntity.badRequest().body(responseEmpresa);
		}
		
		responseEmpresa.setPaginacao(pageEmpresa);
		return ResponseEntity.ok(responseEmpresa);
	}	
	
}