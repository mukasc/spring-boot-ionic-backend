package com.muka.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muka.cursomc.domain.Categoria;
import com.muka.cursomc.repositories.CategoriaRepository;
import com.muka.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	//public Optional<Categoria> find(Integer id) {
	//	Optional<Categoria> obj = repo.findById(id);
		//return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto nao encontrado! " + id + ", Tipo: " + Categoria.class.getName());
		//}
		//return obj;
	//} 
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
	} 
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	
}