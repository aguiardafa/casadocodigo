package br.com.casadocodigo.lojavirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.models.Produto;

@Controller
public class produtosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@RequestMapping("/produtos/form")
	public String form() {
		return "produtos/form";
	}
	
	@RequestMapping("/produtos")
	public String gravar(Produto produto) {
		// Spring fazendo o binde
		System.out.println(produto);
		System.out.println("Titutlo: " + produto.getTitulo());
		System.out.println("Descricao: " + produto.getDescricao());
		System.out.println("Páginas: " + produto.getPaginas());
		
		produtoDAO.gravar(produto);
		
		return "produtos/ok";
	}

}
