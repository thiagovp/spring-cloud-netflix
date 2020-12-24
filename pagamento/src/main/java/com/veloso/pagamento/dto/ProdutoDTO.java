package com.veloso.pagamento.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.veloso.pagamento.entity.Produto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({ "id", "estoque" })
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProdutoDTO extends RepresentationModel<VendaDTO> implements Serializable {

	private static final long serialVersionUID = -8288143912913117080L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("estoque")
	private Integer estoque;
	
	public static ProdutoDTO create(Produto produto) {
		return new ModelMapper().map(produto, ProdutoDTO.class);
	}

}
