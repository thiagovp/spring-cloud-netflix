package com.veloso.pagamento.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veloso.pagamento.dto.VendaDTO;
import com.veloso.pagamento.services.VendaService;


@RestController
@RequestMapping("/venda")
public class VendaController {
	
	
	private final VendaService vendaService;
	private final PagedResourcesAssembler<VendaDTO> assembler;

	@Autowired
	public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaDTO> assembler) {
		super();
		this.vendaService = vendaService;
		this.assembler = assembler;
	}

	@GetMapping(value = "/{id}", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public VendaDTO findById(@PathVariable("id") Long id) {
		VendaDTO vendaDTO = vendaService.findById(id);
		vendaDTO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
		return vendaDTO;
	}

	@GetMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<PagedModel<EntityModel<VendaDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "data"));

		Page<VendaDTO> vendas = vendaService.findAll(pageable);

		vendas.stream()
				.forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));

		PagedModel<EntityModel<VendaDTO>> pagedModel = assembler.toModel(vendas);

		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PostMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" },
			consumes = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, "application/x-yaml" })
	public VendaDTO create(@RequestBody VendaDTO vendaDTO) {
		VendaDTO vendaCriadaDto = vendaService.create(vendaDTO);
		vendaCriadaDto.add(linkTo(methodOn(VendaController.class).findById(vendaCriadaDto.getId())).withSelfRel());
		return vendaCriadaDto;
	}

}
