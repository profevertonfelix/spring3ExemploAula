package com.web2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web2.dto.produtosRecordDto;
import com.web2.model.produtosModel;
import com.web2.repositories.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
public class ProdutosController {
	
	@Autowired
	ProdutosRepository repositorio;
	
	@PostMapping("/produtos")
	public ResponseEntity<produtosModel> 
		salvarProduto(@RequestBody @Valid produtosRecordDto produtosDTO){
		var produtosModel = new produtosModel();
		BeanUtils.copyProperties(produtosDTO, produtosModel);
		return 
	ResponseEntity.status(HttpStatus.CREATED).body(repositorio.save(produtosModel));
	}
		
	@GetMapping("/produtos")
	public ResponseEntity<List<produtosModel>> listarProdutos(){
		return ResponseEntity.status(HttpStatus.OK).body(repositorio.findAll());
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> listarUmProduto(@PathVariable(value="id") int id){
		Optional<produtosModel> produto = repositorio.findById(id);
		if(produto.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		
		return ResponseEntity.status(HttpStatus.OK).body(produto.get());
		
	}
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> deletarProduto(@PathVariable(value="id") int id){
		Optional<produtosModel> produto = repositorio.findById(id);
		if(produto.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		repositorio.delete(produto.get());
		return ResponseEntity.status(HttpStatus.OK).body("Produto deletado");		
	}
	@PutMapping("/produtos/{id}")
	public ResponseEntity<Object> 
	editarProduto(@RequestBody @Valid produtosRecordDto produtoDTO, 
			@PathVariable(value="id") int id){
		Optional<produtosModel> produto = repositorio.findById(id);
		if(produto.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		var produtoModel = produto.get();
		BeanUtils.copyProperties(produtoDTO, produtoModel);
		return ResponseEntity.status(HttpStatus.OK).body(repositorio.save(produtoModel));
	}
}




