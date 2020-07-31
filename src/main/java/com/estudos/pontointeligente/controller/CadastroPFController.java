package com.estudos.pontointeligente.controller;

import java.util.Optional;

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

import com.estudos.pontointeligente.dto.CadastroPFDto;
import com.estudos.pontointeligente.entities.Empresa;
import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.enums.PerfilEnum;
import com.estudos.pontointeligente.response.Response;
import com.estudos.pontointeligente.services.EmpresaService;
import com.estudos.pontointeligente.services.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPFController() {
	}

	@PostMapping(value = "/cadastrar-pf")
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPfDto,
			BindingResult result) {
		Response<CadastroPFDto> response = new Response<CadastroPFDto>();

		validasDadosExistentes(cadastroPfDto, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPfDto, result);

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		Optional<Empresa> auxEmpresa = this.empresaService.buscarPorCnpj(cadastroPfDto.getCnpj());
		auxEmpresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.salvar(funcionario);

		response.setData(this.converterCadastroPfDto(funcionario));
		return ResponseEntity.ok(response);
	}

	private void validasDadosExistentes(CadastroPFDto cadastroPfDto, BindingResult result) {
		
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPfDto.getCnpj());
		if (!empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada!"));
		}
		
		this.funcionarioService.buscarPorCpf(cadastroPfDto.getCpf())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "CPF já existe")));

		this.funcionarioService.buscarPorEmail(cadastroPfDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "E-mail já existe")));
	}
	
	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPfDto, BindingResult result) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf(cadastroPfDto.getCpf());
		funcionario.setNome(cadastroPfDto.getNome());
		funcionario.setEmail(cadastroPfDto.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(cadastroPfDto.getSenha());
		return funcionario;
	}
	
	private CadastroPFDto converterCadastroPfDto(Funcionario funcionario) {
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		cadastroPFDto.setId(funcionario.getId());
		cadastroPFDto.setNome(funcionario.getNome());
		cadastroPFDto.setCpf(funcionario.getCpf());
		cadastroPFDto.setEmail(funcionario.getEmail());
		cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());
		return cadastroPFDto;
	}
}
