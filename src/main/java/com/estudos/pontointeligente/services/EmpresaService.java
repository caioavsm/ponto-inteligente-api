package com.estudos.pontointeligente.services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.estudos.pontointeligente.entities.Empresa;

public interface EmpresaService {
	
	Optional<Empresa> buscarPorCnpj(String cpnj);
	Empresa salvar(Empresa empresa);
	Page<Empresa> listar();
}
