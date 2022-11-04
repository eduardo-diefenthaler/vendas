package br.com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.spring.entity.ItemPedido;

public interface ItemPedidoRepo extends JpaRepository<ItemPedido, Integer> {

}
