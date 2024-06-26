package com.web2.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
				BindingResult result, @RequestParam("file") MultipartFile imagem, 
				RedirectAttributes msg){
		if(result.hasErrors()) {
			msg.addFlashAttribute("erroinserir", "Erro ao realizar o cadastro.");
			return "redirect:/produtos/insert";
		}
		var produtosModel = new produtosModel();
		BeanUtils.copyProperties(produtosDTO, produtosModel);
		try {
			if(!imagem.isEmpty()) {
				byte[] bytes = imagem.getBytes();
				Path caminho = Paths.get
						("./src/main/resources/static/img/"+imagem.getOriginalFilename());
				Files.write(caminho, bytes);
				produtosModel.setImagem(imagem.getOriginalFilename());
			}
		}catch(IOException e) {
			System.out.println("Erro na imagem");
		}
		
		repositorio.save(produtosModel);
		msg.addFlashAttribute("inserirok", "Inserido com sucesso!");
		return "redirect:/produtos/list";
	}
	@GetMapping("/list")
	public ModelAndView listarProdutos(){
		ModelAndView mv = new ModelAndView("produtos/listar");
		List<produtosModel> produtos = 	repositorio.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	@PostMapping("/list")
	public ModelAndView listarProdutosSRC
	(@RequestParam("src") String src){
		ModelAndView mv = new ModelAndView("produtos/listar");
		List<produtosModel> produtos = 
				repositorio.findProdutosByNomeLike("%"+src+"%");
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@GetMapping("/imagem/{imagem}")
	@ResponseBody
	public byte[] mostraImagem(@PathVariable("imagem") String imagem) throws IOException {
		File nomeArquivo = 
				new File("./src/main/resources/static/img/"+imagem);
		if(imagem != null || imagem.trim().length()>0) {
			return Files.readAllBytes(nomeArquivo.toPath());
		}
		return null;
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
