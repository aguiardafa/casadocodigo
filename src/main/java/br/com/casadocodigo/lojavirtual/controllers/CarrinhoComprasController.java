package br.com.casadocodigo.lojavirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.models.CarrinhoCompras;
import br.com.casadocodigo.lojavirtual.models.CarrinhoItem;
import br.com.casadocodigo.lojavirtual.models.Produto;
import br.com.casadocodigo.lojavirtual.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoComprasController {
	@Autowired
	private ProdutoDAO produtoDAO;
	@Autowired
	private CarrinhoCompras carrinhoCompras;

	@RequestMapping("/add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/produtos");
		
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
		carrinhoCompras.add(carrinhoItem);
		
		return modelAndView;
	}

	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = produtoDAO.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		return carrinhoItem;
	}
}
