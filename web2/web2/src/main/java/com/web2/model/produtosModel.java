package com.web2.model;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="produtos")
public class produtosModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int PRODUTOID;
	
	private String nome;
	
	private String descricao;
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "categoriaID")
	private CategoriasModel categoriaModel;
	
	
	public CategoriasModel getCategoriaModel() {
		return categoriaModel;
	}
	public void setCategoriaModel(CategoriasModel categoriaModel) {
		this.categoriaModel = categoriaModel;
	}
	public int getPRODUTOID() {
		return PRODUTOID;
	}
	public void setPRODUTOID(int iD) {
		PRODUTOID = iD;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
