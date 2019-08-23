package com.veiculo.Crud.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veiculo.Crud.model.Opcional;
import com.veiculo.Crud.repository.OpcionalRepository;

@Controller
public class OpcionalController {

	@Autowired
	private OpcionalRepository or;	
	
	@RequestMapping(value="/opcionais", method=RequestMethod.GET)
	public String form() {
		return "opcional/formOpcional";
	}
	
	@RequestMapping(value="/opcionais", method=RequestMethod.POST)
	public String form(@Valid Opcional opcional, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()){
			attributes.addFlashAttribute("mensagem", "Verifique os campos, pois eles não podem estar em branco!");
			return "redirect:/opcionais";
		}
		or.save(opcional);		
		attributes.addFlashAttribute("mensagem", "Opcional cadastrado com sucesso!");
		return "redirect:/veiculos";
	}		
}
