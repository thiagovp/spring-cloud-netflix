package com.veloso.crud.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.veloso.crud.data.dto.ProdutoDTO;
import com.veloso.crud.entity.Produto;
import com.veloso.crud.exception.ResourceNotFoundException;
import com.veloso.crud.message.ProdutoSendMessage;
import com.veloso.crud.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	
	private final ProdutoSendMessage produtoSendMessage;

	//Tentativa de usar o Mapstruct para mapeamento de classes DTO
	//private final ProdutoMapper mapper = ProdutoMapper.INSTANCE;

	public ProdutoDTO create(ProdutoDTO produtoDTO) {
		ProdutoDTO produtoDTORetorno = ProdutoDTO.create(produtoRepository.save(Produto.create(produtoDTO)));
		produtoSendMessage.sendMessage(produtoDTORetorno);
		return produtoDTORetorno;
	}

	public Page<ProdutoDTO> findAll(Pageable pageable) {
		var page = produtoRepository.findAll(pageable);
		return page.map(this::convertToProdutoDTO);
	}

	private ProdutoDTO convertToProdutoDTO(Produto produto) {
		return ProdutoDTO.create(produto);
	}

	public ProdutoDTO findById(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(ResourceNotFoundException::new);
		return ProdutoDTO.create(entity);
	}

	public ProdutoDTO update(ProdutoDTO produtoDTO) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoDTO.getId());

		if (!optionalProduto.isPresent()) {
			throw new ResourceNotFoundException();
		}

		return ProdutoDTO.create(produtoRepository.save(Produto.create(produtoDTO)));
	}

	public void delete(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(ResourceNotFoundException::new);

		produtoRepository.delete(entity);
	}

}
