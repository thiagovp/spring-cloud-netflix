package com.veloso.crud.mappers;

//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;

import com.veloso.crud.data.dto.ProdutoDTO;
import com.veloso.crud.entity.Produto;

//@Mapper
public interface ProdutoMapper {
	
	
//	public static final ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
	
	ProdutoDTO produtoToProdutoDTO(Produto produto);
	
	Produto produtoDTOToProduto(ProdutoDTO produto);
}
