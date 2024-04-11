package com.web2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.model.produtosModel;

@Repository
public interface ProdutosRepository 
	extends JpaRepository<produtosModel, Integer> {
	List<produtosModel> findProdutosByNomeLike(String nome);
}
