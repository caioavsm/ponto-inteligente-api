package com.estudos.pontointeligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.estudos.pontointeligente.entities.Funcionario;

@Transactional(readOnly = true)
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	Funcionario findByCpf(String cpf);
	Funcionario findByEmail(String email);
	List<Funcionario> allFuncionarios();
	
}
