package br.com.casadocodigo.lojavirtual.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.models.Produto;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDAO;

	@RequestMapping("/")
	@Cacheable(value="produtosHome")
	public ModelAndView index() {
		System.out.println("Exibindo a Home da CDC");
		
		ModelAndView modelAndView = new ModelAndView("home");
		
		List<Produto> produtos = produtoDAO.listar();
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
}
