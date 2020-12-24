package com.veloso.pagamento.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.veloso.pagamento.entity.Venda;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id","data","produtos","valorTotal"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VendaDTO extends RepresentationModel<VendaDTO> implements Serializable {
	
	private static final long serialVersionUID = 2430689100882977183L;

	@JsonProperty("id")
	private Long id;

	@JsonProperty("data")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;
	
	@JsonProperty("produtos")
	private List<ProdutoVendaDTO> produtos;
	
	@JsonProperty("valorTotal")
	private Double valorTotal;
	
	public static VendaDTO create(Venda venda) {
		return new ModelMapper().map(venda, VendaDTO.class);
	}

}
