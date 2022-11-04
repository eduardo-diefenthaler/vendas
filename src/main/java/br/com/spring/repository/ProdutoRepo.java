package br.com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.spring.entity.Produto;

public interface ProdutoRepo extends JpaRepository<Produto, Integer> {

}
