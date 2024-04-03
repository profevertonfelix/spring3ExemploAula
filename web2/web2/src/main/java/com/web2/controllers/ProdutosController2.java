package com.web2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web2.dto.produtosRecordDto;
import com.web2.model.CategoriasModel;
import com.web2.model.produtosModel;
import com.web2.repositories.CategoriasRepository;
import com.web2.repositories.ProdutosRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutosController2 {

	@Autowired
	ProdutosRepository repositorio;
	
	@Autowired
	CategoriasRepository categoriasRepositorio;
	
	
	/*@GetMapping("/insert")
	public String formulario() {
		return "produtos/inserir";
	}*/
	
	
	@GetMapping("/insert")
	public ModelAndView formulario() {
		ModelAndView mv = new ModelAndView("produtos/inserir");
		List<CategoriasModel> categorias = 	
				categoriasRepositorio.findAll();
		mv.addObject("categorias", categorias);
		return mv;
	}
	
	@PostMapping("/insert")
	public String salvarProduto
		(@ModelAttribute @Valid produtosRecordDto produtosDTO,
				BindingResult result){
		if(result.hasErrors()) {
			return "redirect:/produtos/insert";
		}
		var produtosModel = new produtosModel();
		BeanUtils.copyProperties(produtosDTO, produtosModel);
		repositorio.save(produtosModel);
		return "redirect:/produtos/list";
	}
	@GetMapping("/list")
	public ModelAndView listarProdutos(){
		ModelAndView mv = new ModelAndView("produtos/listar");
		List<produtosModel> produtos = 	repositorio.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduto(@PathVariable(value="id") int id){

		Optional<produtosModel> productO = repositorio.findById(id);
		if(productO.isEmpty()) {
			return "redirect:/produtos/list";
		}
		repositorio.delete(productO.get());
		return "redirect:/produtos/list";
	}
}
