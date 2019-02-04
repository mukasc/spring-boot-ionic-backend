package com.muka.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.muka.cursomc.domain.ItemPedido;
import com.muka.cursomc.domain.PagamentoComBoleto;
import com.muka.cursomc.domain.Pedido;
import com.muka.cursomc.domain.enums.EstadoPagamento;
import com.muka.cursomc.repositories.ItemPedidoRepository;
import com.muka.cursomc.repositories.PagamentoRepository;
import com.muka.cursomc.repositories.PedidoRepository;
import com.muka.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	//public Optional<Pedido> find(Integer id) {
	//	Optional<Pedido> obj = repo.findById(id);
		//return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto nao encontrado! " + id + ", Tipo: " + Pedido.class.getName());
		//}
		//return obj;
	//} 
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(    "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preecherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find((ip.getProduto().getId())).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}
}