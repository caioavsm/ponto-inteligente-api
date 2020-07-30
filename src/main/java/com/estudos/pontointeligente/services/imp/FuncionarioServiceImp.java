package com.estudos.pontointeligente.services.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.repositories.FuncionarioRepository;
import com.estudos.pontointeligente.services.FuncionarioService;

public class FuncionarioServiceImp implements FuncionarioService{

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Override
	public Funcionario salvar(Funcionario funcionario) {
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
	}

	@Override
	public List<Funcionario> allFuncionarios() {
		return funcionarioRepository.findAll();
	}

}
