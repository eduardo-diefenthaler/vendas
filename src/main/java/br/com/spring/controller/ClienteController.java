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

import br.com.spring.entity.Cliente;
import br.com.spring.repository.ClientesRepo;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	private final ClientesRepo clientesRepo;

	public ClienteController(ClientesRepo clientesRepo) {
		this.clientesRepo = clientesRepo;
	}

	@GetMapping("/{id}")
	public Cliente getClienteById(@PathVariable Integer id) {
		Optional<Cliente> cliente = clientesRepo.findById(id);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> find(Cliente clienteFiltro) {
		ExampleMatcher exampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Cliente> example = Example.of(clienteFiltro, exampleMatcher);
		return clientesRepo.findAll(example);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody Cliente cliente) {
		return clientesRepo.save(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		Optional<Cliente> cliente = clientesRepo.findById(id);
		if (cliente.isPresent()) {
			clientesRepo.delete(cliente.get());
			return;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody Cliente cliente) {
		Optional<Cliente> clienteExistente = clientesRepo.findById(id);
		if (clienteExistente.isPresent()) {
			cliente.setId(clienteExistente.get().getId());
			clientesRepo.save(cliente);
			return;
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!");
	}

}
