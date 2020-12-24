package com.veloso.pagamento.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.veloso.pagamento.dto.ProdutoDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "produto")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto{
	
	@Id
	private Long id;
	
	@Column(name="estoque", nullable = false, length = 10)
	private Integer estoque;
	
	public static Produto create(ProdutoDTO produtoDto) {
		return new ModelMapper().map(produtoDto, Produto.class);
	}

}