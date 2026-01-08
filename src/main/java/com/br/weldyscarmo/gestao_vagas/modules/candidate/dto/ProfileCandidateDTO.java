package com.br.weldyscarmo.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateDTO {

    @Schema(example = "Maria dos Santos")
    private String name;

    @Schema(example = "maria")
    private String username;

    @Schema(example = "maria@gmail.com")
    private String email;

    @Schema(example = "Desenvolvedora Java em busca da primeira oportunidade")
    private String description;
    private UUID id;
}
