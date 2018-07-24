package br.com.casadocodigo.lojavirtual.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.models.Produto;
import br.com.casadocodigo.lojavirtual.models.TipoPreco;
import br.com.casadocodigo.lojavirtual.validations.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tiposPreco", TipoPreco.values());
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		// Spring faz o bind automático
		System.out.println(produto);
		System.out.println("Titutlo: " + produto.getTitulo());
		System.out.println("Descricao: " + produto.getDescricao());
		System.out.println("Páginas: " + produto.getPaginas());
		
		// validando os dados
		if(result.hasErrors()) {
			return form(produto);
		}
		// grava no banco através do DAO
		produtoDAO.gravar(produto);
		// usando Flash - ele só dura até a próxima requisição
		redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
		
		// Para evitar o bug do F5:  Always redirect after POST
		// (em português, significa Sempre redirecione após POST)
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar() {
		List<Produto> produtos = produtoDAO.listar();
		
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}

}
