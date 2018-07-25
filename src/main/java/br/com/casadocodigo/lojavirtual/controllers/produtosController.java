package br.com.casadocodigo.lojavirtual.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.infra.FileSaver;
import br.com.casadocodigo.lojavirtual.models.Produto;
import br.com.casadocodigo.lojavirtual.models.TipoPreco;
import br.com.casadocodigo.lojavirtual.validations.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	@Autowired
	private FileSaver fileSaver;
	
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
	public ModelAndView gravar(MultipartFile sumario , @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
		// Spring faz o bind automático
		System.out.println(produto);
		System.out.println("Titutlo: " + produto.getTitulo());
		System.out.println("Descricao: " + produto.getDescricao());
		System.out.println("Páginas: " + produto.getPaginas());
		// recebimento do arquivo do sumário
		System.out.println(sumario.getOriginalFilename());
		
		// validando os dados
		if(result.hasErrors()) {
			return form(produto);
		}
		
		// gravar o arquivo do sumario no servidor
		String pathSumario = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(pathSumario);
		
		// gravar os dados do produto no banco através do DAO
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
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		
		Produto produto = produtoDAO.find(id);
		modelAndView.addObject("produto", produto);
		
		return modelAndView;
	}

}
