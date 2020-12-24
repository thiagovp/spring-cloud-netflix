package com.veloso.pagamento.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.veloso.pagamento.dto.ProdutoDTO;
import com.veloso.pagamento.entity.Produto;
import com.veloso.pagamento.repository.ProdutoRepository;

@Component
public class ProdutoReceiveMessage {
	
	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
		super();
		this.produtoRepository = produtoRepository;
	}
	
	@RabbitListener(queues = {"${crud.rabbitmq.queue}"})
	public void receive(@Payload ProdutoDTO produtoDTO) {
		produtoRepository.save(Produto.create(produtoDTO));
	}
	
	

}
