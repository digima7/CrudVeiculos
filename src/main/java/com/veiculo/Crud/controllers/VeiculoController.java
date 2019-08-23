package com.veiculo.Crud.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veiculo.Crud.model.Modelo;
import com.veiculo.Crud.model.Opcional;
import com.veiculo.Crud.model.Veiculo;
import com.veiculo.Crud.repository.ModeloRepository;
import com.veiculo.Crud.repository.OpcionalRepository;
import com.veiculo.Crud.repository.VeiculoRepository;

@Controller
public class VeiculoController {

	@Autowired
	private VeiculoRepository vr;
	
	@Autowired
	private OpcionalRepository or;
	
	@Autowired
	private ModeloRepository mr;
	
	private long idVeiculoPagina = 0;
	
	@RequestMapping(value="/CadastrarVeiculo", method=RequestMethod.GET)
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("veiculo/formVeiculo");
		Iterable<Modelo> modelos = mr.findAll();
		mv.addObject("modelos", modelos);
		
		return mv;
	}
	
	@RequestMapping(value="/CadastrarVeiculo", method=RequestMethod.POST)
	public String form(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes attributes, @RequestParam(name = "SelectModelo", defaultValue = "-1") int idModelo) {
		if(result.hasErrors()){
			if (idModelo == -1) {
				attributes.addFlashAttribute("mensagem", "Selecione um modelo, ou caso necessário, você precisará cadastrar um modelo novo na listagem dos veiculos!");
			} else {
			attributes.addFlashAttribute("mensagem", "Verifique os campos, pois eles não podem estar em branco!");
			}
			return "redirect:/CadastrarVeiculo";
		}
		
		veiculo.setData_cadastro(new Date());
		Modelo modelo = new Modelo();
		modelo.setId(idModelo);
		veiculo.setModelo(modelo);
		vr.save(veiculo);		
		attributes.addFlashAttribute("mensagem", "Veiculo cadastrado com sucesso!");
		return "redirect:/veiculos";
	}
	
	@RequestMapping("/veiculos")
	public ModelAndView listaVeiculos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Veiculo> veiculos = vr.findAll();
		mv.addObject("veiculos", veiculos);
		
		return mv;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView opcionaisVeiculo(@PathVariable("id") long id) {
		idVeiculoPagina = id;
		Veiculo veiculo = vr.findById(id);
		ModelAndView mv = new ModelAndView("veiculo/opcionaisVeiculo");
		mv.addObject("veiculo", veiculo);	
				
		Iterable<Opcional> opcionais = comboOpcional(veiculo);
		mv.addObject("opcionais", opcionais);		
		return mv;
		
	}
	
	@RequestMapping("/deletar")
	public String deletarVeiculo(long id) {
		Veiculo veiculo = vr.findById(id);
		vr.delete(veiculo);
		return "redirect:/veiculos";
	}
	
	@RequestMapping("/deletarOp")
	public String deletarOpcional(long idOpcional) {		
		Veiculo veiculo = vr.findById(idVeiculoPagina);
		Opcional opcional = or.findById(idOpcional);		 
		veiculo.getOpcionais().remove(opcional);	
		
		vr.save(veiculo);
		return "redirect:/"+idVeiculoPagina;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String opcionaisVeiculoPost(@PathVariable("id") long id, @RequestParam(name = "SelectOpcional", required = false) int idOpcional) {		
		Veiculo veiculo = vr.findById(id);
		Opcional opcional = new Opcional();
		opcional.setId(idOpcional);
		
		veiculo.getOpcionais().add(opcional);
		vr.save(veiculo);		
		return "redirect:/{id}";
		
	}
	
	private Iterable<Opcional> comboOpcional(Veiculo veiculo){
		List<Opcional> lstResult =new ArrayList<Opcional>();
		List<Opcional> lstTodosOpcionais = or.findAll();
		List<Opcional> lstVeiculo = veiculo.getOpcionais();
		
		for (Opcional opcional : lstTodosOpcionais) {
			if (lstVeiculo.indexOf(opcional) < 0) {
				lstResult.add(opcional);
			}
		}
		return lstResult;
	}
}
