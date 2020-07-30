package com.estudos.pontointeligente.services.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.estudos.pontointeligente.entities.Empresa;
import com.estudos.pontointeligente.repositories.EmpresaRepository;
import com.estudos.pontointeligente.services.EmpresaService;

public class EmpresaServiceImp implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa salvar(Empresa empresa) {
		return this.empresaRepository.save(empresa);
	}

}
