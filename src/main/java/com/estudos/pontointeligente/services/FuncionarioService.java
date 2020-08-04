package com.estudos.pontointeligente.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.estudos.pontointeligente.entities.Funcionario;

public interface FuncionarioService {
	
	Funcionario salvar(Funcionario funcionario);
	Optional<Funcionario> buscarPorCpf(String cpf);
	Optional<Funcionario> buscarPorEmail(String email);
	Optional<Funcionario> buscarPorId(Long id);
	Page<Funcionario> listar();
}
