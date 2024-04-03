package com.web2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web2.model.CategoriasModel;
import com.web2.model.produtosModel;

@Repository
public interface CategoriasRepository 
	extends JpaRepository<CategoriasModel, Integer> {

}
