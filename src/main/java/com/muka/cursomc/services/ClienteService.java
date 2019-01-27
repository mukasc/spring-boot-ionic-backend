package com.muka.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muka.cursomc.domain.Cliente;
import com.muka.cursomc.repositories.ClienteRepository;
import com.muka.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	//public Optional<Categoria> find(Integer id) {
	//	Optional<Categoria> obj = repo.findById(id);
		//return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto nao encontrado! " + id + ", Tipo: " + Categoria.class.getName());
		//}
		//return obj;
	//} 
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
	} 
}