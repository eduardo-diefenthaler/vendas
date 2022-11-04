package br.com.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.spring.dto.ItemPedidoDTO;
import br.com.spring.dto.PedidoDTO;
import br.com.spring.entity.ItemPedido;
import br.com.spring.entity.Pedido;
import br.com.spring.repository.ClientesRepo;
import br.com.spring.repository.ItemPedidoRepo;
import br.com.spring.repository.PedidoRepo;
import br.com.spring.repository.ProdutoRepo;

@Service
public class PedidoService {

	private final PedidoRepo pedidoRepo;

	private final ClientesRepo clientesRepo;

	private final ProdutoRepo produtoRepo;

	private final ItemPedidoRepo itemPedidoRepo;

	public PedidoService(PedidoRepo pedidoRepo, ClientesRepo clientesRepo, ProdutoRepo produtoRepo, ItemPedidoRepo itemPedidoRepo) {
		this.pedidoRepo = pedidoRepo;
		this.clientesRepo = clientesRepo;
		this.produtoRepo = produtoRepo;
		this.itemPedidoRepo = itemPedidoRepo;
	}

	@Transactional
	public Pedido save(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setDataPedidio(LocalDate.now());
		pedido.setCliente(clientesRepo.findById(pedidoDTO.getCliente()).get());

		List<ItemPedido> itemPedidos;
		itemPedidos = itensListToCollection(pedidoDTO.getItens(), pedido);

		pedidoRepo.save(pedido);
		itemPedidoRepo.saveAll(itemPedidos);
		pedido.setItensPedido(itemPedidos);
		return pedido;
	}

	private List<ItemPedido> itensListToCollection(List<ItemPedidoDTO> itensPedidoList, Pedido pedido) {
		return itensPedidoList.stream().map(itemPedidoDTO -> {
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produtoRepo.findById(itemPedidoDTO.getProduto()).get());
			itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
			return itemPedido;
		}).collect(Collectors.toList());
	}

}
