package br.com.casadocodigo.lojavirtual.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.lojavirtual.models.CarrinhoCompras;
import br.com.casadocodigo.lojavirtual.models.DadosPagamento;

@Controller
@RequestMapping("/pagamento")
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Callable<ModelAndView> finalizarCompra(RedirectAttributes model) {
		return () -> {
			if(carrinho.getQuantidade() > 0) {
				// serviço externo de conclusão do pagamento
				String uri = "http://book-payment.herokuapp.com/payment";
				
				try {
					// RestTemplate possibilita a aplicação consumir um serviço terceiro
					// neste caso, para a conclusão do pagamento
					String response = restTemplate.postForObject(uri, 
							new DadosPagamento(carrinho.getTotal()),
							String.class);
					
					System.out.println(response);
					System.out.println("Valor total da compra: " + carrinho.getTotal());
					System.out.println("Quant. total da compra: " + carrinho.getQuantidade());
					
					model.addFlashAttribute("sucesso", response);
					return new ModelAndView("redirect:/produtos");
				} catch (HttpClientErrorException e) {
					e.printStackTrace();
					
					model.addFlashAttribute("falha", "Valor maior que o permitido");
					return new ModelAndView("redirect:/produtos");
				}
			}else {
				model.addFlashAttribute("falha", "Nenhum item foi adicionado a compra");
				return new ModelAndView("redirect:/produtos"); 
			}
		};
	}
}
