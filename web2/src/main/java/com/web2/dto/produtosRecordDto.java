package com.web2.dto;

import com.web2.model.CategoriasModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record produtosRecordDto(@NotBlank String nome, @NotBlank String descricao, @NotNull boolean status, CategoriasModel categoriaID, String imagem) {

}
