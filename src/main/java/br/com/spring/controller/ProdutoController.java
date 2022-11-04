package br.com.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.spring.entity.Produto;
import br.com.spring.repository.ProdutoRepo;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	private final ProdutoRepo produtoRepo;

	public ProdutoController(ProdutoRepo produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Produto findById(@PathVariable Integer id) {
		Optional<Produto> produtoExistente = produtoRepo.findById(id);
		if (produtoExistente.isPresent()) {
			return produtoExistente.get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Produto> find(Produto produtoFiltro) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Produto> example = Example.of(produtoFiltro, exampleMatcher);
		return produtoRepo.findAll(example);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Produto save(@RequestBody Produto produto) {
		return produtoRepo.save(produto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		Optional<Produto> produtoExistente = produtoRepo.findById(id);
		if (produtoExistente.isPresent()) {
			produtoRepo.delete(produtoExistente.get());
			return;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!");
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody Produto produto) {
		Optional<Produto> produtoExistente = produtoRepo.findById(id);
		if (produtoExistente.isPresent()) {
			produto.setId(produtoExistente.get().getId());
			produtoRepo.save(produto);
			return;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!");
	}

}
