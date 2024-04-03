package com.web2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record produtosRecordDto(@NotBlank String nome, @NotBlank String descricao, @NotNull boolean status) {

}
