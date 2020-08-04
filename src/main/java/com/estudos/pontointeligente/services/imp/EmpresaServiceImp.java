package com.estudos.pontointeligente.services.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.estudos.pontointeligente.entities.Empresa;
import com.estudos.pontointeligente.repositories.EmpresaRepository;
import com.estudos.pontointeligente.services.EmpresaService;

@Service
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

	@Override
	public Page<Empresa> listar() {
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "razaoSocial");
		return this.empresaRepository.findAll(pageRequest);
	}

}
