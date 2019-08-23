package com.veiculo.Crud.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veiculo.Crud.model.Modelo;
import com.veiculo.Crud.repository.ModeloRepository;

@Controller
public class ModeloController {

	@Autowired
	private ModeloRepository mr;	
	
	@RequestMapping(value="/modelos", method=RequestMethod.GET)
	public String form() {
		return "modelo/formModelo";
	}
	
	@RequestMapping(value="/modelos", method=RequestMethod.POST)
	public String form(@Valid Modelo modelo, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos, pois eles não podem estar em branco!");
			return "redirect:/modelos";
		}
		mr.save(modelo);		
		attributes.addFlashAttribute("mensagem", "Modelo cadastrado com sucesso!");
		return "redirect:/veiculos";
	}
	
}
