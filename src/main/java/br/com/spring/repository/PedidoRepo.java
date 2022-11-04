package br.com.spring.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.spring.entity.Cliente;
import br.com.spring.entity.Pedido;

public interface PedidoRepo extends JpaRepository<Pedido, Integer> {

	Collection<Pedido> findByCliente(Cliente cliente);

}
