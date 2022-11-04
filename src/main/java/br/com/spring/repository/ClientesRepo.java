package br.com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.spring.entity.Cliente;

public interface ClientesRepo extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByNomeLike(String nome);

	@Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
	Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
