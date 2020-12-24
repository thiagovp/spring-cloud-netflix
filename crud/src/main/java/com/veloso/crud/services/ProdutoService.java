package com.veloso.crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.veloso.crud.data.dto.ProdutoDTO;
import com.veloso.crud.entity.Produto;
import com.veloso.crud.exception.ResourceNotFoundException;
import com.veloso.crud.message.ProdutoSendMessage;
import com.veloso.crud.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	
	private final ProdutoSendMessage produtoSendMessage;

	//Tentativa de usar o Mapstruct para mapeamento de classes DTO
	//private final ProdutoMapper mapper = ProdutoMapper.INSTANCE;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage) {
		super();
		this.produtoRepository = produtoRepository;
		this.produtoSendMessage = produtoSendMessage;
	}

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
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return ProdutoDTO.create(entity);
	}

	public ProdutoDTO update(ProdutoDTO produtoDTO) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoDTO.getId());

		if (!optionalProduto.isPresent()) {
			new ResourceNotFoundException("No records found for this ID");
		}

		return ProdutoDTO.create(produtoRepository.save(Produto.create(produtoDTO)));
	}

	public void delete(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		produtoRepository.delete(entity);
	}

}
