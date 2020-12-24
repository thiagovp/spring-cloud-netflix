package com.veloso.crud.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veloso.crud.data.dto.ProdutoDTO;
import com.veloso.crud.services.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private final ProdutoService produtoService;
	private final PagedResourcesAssembler<ProdutoDTO> assembler;

	@Autowired
	public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoDTO> assembler) {
		super();
		this.produtoService = produtoService;
		this.assembler = assembler;
	}

	@GetMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ProdutoDTO findById(@PathVariable("id") Long id) {
		ProdutoDTO produtoDTO = produtoService.findById(id);
		produtoDTO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
		return produtoDTO;
	}

	@GetMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));

		Page<ProdutoDTO> produtos = produtoService.findAll(pageable);

		produtos.stream()
				.forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

		PagedModel<EntityModel<ProdutoDTO>> pagedModel = assembler.toModel(produtos);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PostMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" },
			consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ProdutoDTO create(@RequestBody ProdutoDTO produtoDTO) {
		ProdutoDTO proDto = produtoService.create(produtoDTO);
		proDto.add(linkTo(methodOn(ProdutoController.class).findById(proDto.getId())).withSelfRel());
		return proDto;
	}
	
	@PutMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" },
			consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ProdutoDTO update(@RequestBody ProdutoDTO produtoDTO) {
		ProdutoDTO proDto = produtoService.update(produtoDTO);
		proDto.add(linkTo(methodOn(ProdutoController.class).findById(proDto.getId())).withSelfRel());
		return proDto;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		produtoService.delete(id);
		return ResponseEntity.ok().build();
	}

}
