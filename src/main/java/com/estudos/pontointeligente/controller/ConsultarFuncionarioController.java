package com.estudos.pontointeligente.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.pontointeligente.entities.Funcionario;

@RestController
@RequestMapping(value = "/funcionario/listar")
public class ConsultarFuncionarioController {

	@RequestMapping(method = RequestMethod.GET)
	public List<Funcionario> listar() {
		return null;
	}
}
