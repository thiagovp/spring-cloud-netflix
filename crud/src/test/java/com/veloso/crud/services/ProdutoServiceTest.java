package com.veloso.crud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.veloso.crud.data.dto.ProdutoDTO;
import com.veloso.crud.entity.Produto;
import com.veloso.crud.exception.ResourceNotFoundException;
import com.veloso.crud.message.ProdutoSendMessage;
import com.veloso.crud.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ProdutoServiceTest {

	@Autowired
	ProdutoRepository produtoRepository;

	@Mock
	ProdutoSendMessage produtoSendMessage;

	ProdutoService produtoService;

	@BeforeEach
	void setUp() {
		produtoService = new ProdutoService(produtoRepository, produtoSendMessage);
	}

	@Test
	void when_create_produto_it_should_return_produtodto() {
		ProdutoDTO produtoDTO = produtoService.create(retornaProdutoDTONovo());

		assertNotNull(produtoDTO);
		assertNotNull(produtoDTO.getId());

	}

	@Test
	void when_search_for_all_should_return_page_list_of_produtodto() {
		Pageable pageable = PageRequest.of(0, 12, Sort.by(Direction.ASC, "nome"));
		produtoRepository.save(retornaProdutoNovo());

		Page<ProdutoDTO> retorno = produtoService.findAll(pageable);

		assertNotNull(retorno);
		assertNotEquals(0, retorno.getSize());

	}

	@Test
	void when_find_by_id_should_return_produtodto() {
		produtoRepository.save(retornaProdutoNovo());
		ProdutoDTO retorno = produtoService.findById(1L);
		assertNotNull(retorno);
	}

	@Test
	void when_find_by_id_should_return_resource_not_found_exception() {
		assertThrows(ResourceNotFoundException.class, () -> {
			produtoService.findById(100L);
		});

	}

	@Test
	void when_update_should_return_produto_dto() {
		produtoRepository.save(retornaProdutoNovo());
		ProdutoDTO retorno = produtoService.update(retornaProdutoDTOAlterado());

		assertNotNull(retorno);
		assertEquals("teste novo", retorno.getNome());
		assertEquals(29, retorno.getEstoque());
		assertEquals(23.0, retorno.getPreco());

	}

	@Test
	void when_update_should_return_resource_not_found_exception() {
		ProdutoDTO produtoDTO = retornaProdutoDTONaoCadastrado();
		assertThrows(ResourceNotFoundException.class, () -> produtoService.update(produtoDTO));
	}

	@Test
	void when_delete_should_return_resource_not_found_exception() {
		assertThrows(ResourceNotFoundException.class, () -> {
			produtoService.delete(100L);
		});
	}

	@Test
	void when_delete_should_remove_item() {
		produtoService.delete(1L);
		Optional<Produto> produtoOptional = produtoRepository.findById(4L);
		assertTrue(produtoOptional.isEmpty());
	}

	private ProdutoDTO retornaProdutoDTOAlterado() {
		return new ProdutoDTO(2L, "teste novo", 29, 23.0);
	}

	private ProdutoDTO retornaProdutoDTONovo() {
		return new ProdutoDTO(null, "teste2", 22, 22.0);
	}

	private Produto retornaProdutoNovo() {
		return new Produto(null, "teste2", 22, 22.0);
	}

	private ProdutoDTO retornaProdutoDTONaoCadastrado() {
		return new ProdutoDTO(100L, "não cadastrado", 1, 1.0);
	}

}
