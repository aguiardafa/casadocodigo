package br.com.casadocodigo.lojavirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.lojavirtual.models.CarrinhoCompras;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;
	
	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public ModelAndView finalizarCompra(RedirectAttributes model) {
		
		System.out.println(carrinho.getTotal());
		
		model.addFlashAttribute("sucesso", "Compra Realizada com Sucesso");
		
		return new ModelAndView("redirect:/produtos");
	}
}
