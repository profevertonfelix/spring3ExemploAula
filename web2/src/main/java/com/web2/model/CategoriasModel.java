package com.web2.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="categorias")
public class CategoriasModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int CATEGORIAID;
	
	private String nome;
	
	@OneToMany(mappedBy = "PRODUTOID")
	private List<produtosModel> produtos;
	
	public int getCATEGORIAID() {
		return CATEGORIAID;
	}
	public void setCATEGORIAID(int CATEGORIAID) {
		this.CATEGORIAID = CATEGORIAID;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
}
