package faq;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * Application runner.
 * 
 * Include some glue code to support i18n.
 *
 */
@SpringBootApplication
@Configuration
public class Application implements RepositoryRestConfigurer, WebMvcConfigurer {

	public static void main(String[] args) throws Throwable {
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * I18n local resolver based on a cookie.
	 * 
	 * Default set to Local.ENGLISH
	 * 
	 * @return locale resolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver slr = new CookieLocaleResolver();
	    slr.setDefaultLocale(Locale.ENGLISH);
	    return slr;
	}
	
	/**
	 * Add an interceptor to switch locales.
	 * 
	 * Parameter name is <code>lang</code>.
	 * 
	 * @return locale change interceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	/**
	 * Register the local change interceptor with the application registry.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * Define i18n messages in bundles located under <code>messages</code>.
	 * 
	 * @return the application bundle
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	/**
	 * @return i18n validator
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	   LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	   bean.setValidationMessageSource(messageSource());
	   return bean;
	}

}
