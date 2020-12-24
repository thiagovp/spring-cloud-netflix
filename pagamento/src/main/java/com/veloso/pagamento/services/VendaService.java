package com.veloso.pagamento.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.veloso.pagamento.dto.VendaDTO;
import com.veloso.pagamento.entity.ProdutoVenda;
import com.veloso.pagamento.entity.Venda;
import com.veloso.pagamento.exceptions.ResourceNotFoundException;
import com.veloso.pagamento.repository.ProdutoVendaRepository;
import com.veloso.pagamento.repository.VendaRepository;

@Service
public class VendaService {

	private final VendaRepository vendaRepository;
	private final ProdutoVendaRepository produtoVendaRepository;

	@Autowired
	public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
		super();
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository = produtoVendaRepository;
	}

	public VendaDTO create(VendaDTO vendaDTO) {

		Venda venda = vendaRepository.save(Venda.create(vendaDTO));

		List<ProdutoVenda> produtosSalvos = new ArrayList<>();

		vendaDTO.getProdutos().forEach(p -> {
			ProdutoVenda produtoVenda = ProdutoVenda.create(p);
			produtoVenda.setVenda(venda);
			produtosSalvos.add(produtoVendaRepository.save(produtoVenda));

		});
		venda.setProdutos(produtosSalvos);
		return VendaDTO.create(venda);
	}

	public Page<VendaDTO> findAll(Pageable pageable) {
		var page = vendaRepository.findAll(pageable);
		return page.map(this::convertToVendaDTO);
	}

	private VendaDTO convertToVendaDTO(Venda produto) {
		return VendaDTO.create(produto);
	}

	public VendaDTO findById(Long id) {
		var entity = vendaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return VendaDTO.create(entity);
	}

}
