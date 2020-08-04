package com.estudos.pontointeligente.services.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.estudos.pontointeligente.entities.Funcionario;
import com.estudos.pontointeligente.repositories.FuncionarioRepository;
import com.estudos.pontointeligente.services.FuncionarioService;

@Service
public class FuncionarioServiceImp implements FuncionarioService {

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
	public Optional<Funcionario> buscarPorId(Long id) {
		return funcionarioRepository.findById(id);
	}

	@Override
	public Page<Funcionario> listar() {
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
		return funcionarioRepository.findAll(pageRequest);
	}

}
