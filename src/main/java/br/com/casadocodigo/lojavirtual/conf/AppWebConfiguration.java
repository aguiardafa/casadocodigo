package br.com.casadocodigo.lojavirtual.conf;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.lojavirtual.controllers.HomeController;
import br.com.casadocodigo.lojavirtual.daos.ProdutoDAO;
import br.com.casadocodigo.lojavirtual.infra.FileSaver;
import br.com.casadocodigo.lojavirtual.models.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class })
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	// O Spring MVC nega o acesso à pasta resources, para liberar o acesso:
	// 1. A classe deve estender a classe WebMvcConfigurerAdapter
	// 2. A classe deve implementar o método configureDefaultServletHandling para
	// liberar o acesso
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// InternalResourceViewResolver se refere a um resolvedor interno do caminho
	// padrão das views
	// Configura o Spring questoes internas sobre as views
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");

		// expor todos beans para acesso na view
		// resolver.setExposeContextBeansAsAttributes(true);

		// expor um bean específico para acesso na view
		resolver.setExposedContextBeanNames("carrinhoCompras");

		return resolver;
	}

	// Configura o Spring para utilização de um arquivo de mensagens para exibição
	// nas views
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);

		return messageSource;
	}

	// Configura o Spring para utilização de um padrão na conversão e formatação de
	// Datas
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);

		return conversionService;
	}

	// MultipartResolver se refere a um resolvedor de dados multimidia
	// Configura o Spring para trabalhar com arquivos
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	// RestTemplate possibilita a aplicação consumir um serviço terceiro
	// neste caso, para a conclusão do pagamento
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	// CacheManager fornece um gerenciador de cache para que o Spring
	// Configura o Spring para utilização de cache das views
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
}
