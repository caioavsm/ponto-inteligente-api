package com.estudos.pontointeligente.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.pontointeligente.dto.CadastroPJDto;
import com.estudos.pontointeligente.entities.Empresa;
import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.enums.PerfilEnum;
import com.estudos.pontointeligente.response.Response;
import com.estudos.pontointeligente.services.EmpresaService;
import com.estudos.pontointeligente.services.FuncionarioService;

@RestController
@RequestMapping("/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPJController() {
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPjDto,
			BindingResult result) {
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();
		validasDadosExistentes(cadastroPjDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPjDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPjDto, result);
		
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.empresaService.salvar(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.salvar(funcionario);
		
		response.setData(this.converterCadastroPjDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	private void validasDadosExistentes(CadastroPJDto cadastroPjDto, BindingResult result) {
		
		this.empresaService.buscarPorCnpj(cadastroPjDto.getCnpj())
			.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa Já existente")));;
		
		this.funcionarioService.buscarPorCpf(cadastroPjDto.getCpf())
			.ifPresent(emp -> result.addError(new ObjectError("funcionario", "CPF já existe")));
		
		this.funcionarioService.buscarPorEmail(cadastroPjDto.getEmail())
			.ifPresent(emp -> result.addError(new ObjectError("funcionario", "E-mail já existe")));
		
	}
	
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		
		return empresa;
	}
	
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(cadastroPJDto.getSenha());
		
		return funcionario;
	}
	
	private CadastroPJDto converterCadastroPjDto(Funcionario funcionario) {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(funcionario.getId());
		cadastroPJDto.setNome(funcionario.getNome());
		cadastroPJDto.setCpf(funcionario.getCpf());
		cadastroPJDto.setEmail(funcionario.getEmail());
		cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPJDto;
	}
}
