package hello;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import controller.*;
import service.*;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
@EnableRedisHttpSession
@EnableAsync
@EnableCircuitBreaker
public class Application extends AsyncConfigurerSupport{

	public static final String RecipeCommandSide= "http://recipe-command-side/";
	public static final String RecipeQuerySide= "http://recipe-query-side/";
	public static final String CustomerCommandSide= "http://customer-command-side/";
	public static final String CustomerQuerySide= "http://customer-query-side/";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

@Override
public Executor getAsyncExecutor()
{
	ThreadPoolTaskExecutor executors= new ThreadPoolTaskExecutor();
	executors.setCorePoolSize(4);
	executors.setMaxPoolSize(8);
	executors.setQueueCapacity(500);
	executors.initialize();
	return executors;
}

	
	//Spring Security Configuration
	@Configuration
	//annotation to manage the automatic csfr token delivery
	@EnableWebSecurity
	//to replace the http session
	@EnableRedisHttpSession
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers
				("/","/registerCustomerView","/extlink.png",
				"/pivotal-logo-600.png","/pws-header-logo_new.png",
				"/spring-trans.png","/styles.css","/checkSignUp.js","/checkRegistration"
				,"/recipe-command-side/**","/recipe-query-side/**","/customer-command-side/**",
				"/customer-query-side/**","/registerCustomer","/hystrix",
				"/hystrix.stream","/hystrix/index","/hystrix-dashboard")
			.permitAll().anyRequest().authenticated()
			.and().requestCache().requestCache(new NullRequestCache()).and()
			.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
	}

	//thymeleaf security dialect support activation
	@Configuration
	@ConditionalOnClass({SpringSecurityDialect.class})
	protected static class ThymeleafSecurityDialectConfiguration {
		
		protected ThymeleafSecurityDialectConfiguration() {
		}

		@Bean
		@ConditionalOnMissingBean
		public SpringSecurityDialect securityDialect() {
			return new SpringSecurityDialect();
		}
	}

	
	//******************************** RECIPE CONTROLLERS************************************************
	@Bean
	public UpdateRecipeController UpdateController()
	{
		return new UpdateRecipeController(recipeUpdateService(),recipeGetService());
	}


	@Bean
	public GetRecipeController GetControllet()
	{
		return new GetRecipeController(recipeGetService());
	}


	@Bean
	public UploadRecipeController UploadController()
	{
		return new UploadRecipeController(recipeUploadService());
	}


	//******************************** CUSTOMER CONTROLLERS************************************************

	@Bean
	public RegisterCustomerController RegisterCustomerController()
	{
		return new RegisterCustomerController(CustomerUploadService());
	}



	@Bean
	public GetCustomerController GetCustomerController()
	{
		return new GetCustomerController(CustomerGetService());
	}


	//********************************RECIPE SERVICES**********************************************
	@Bean
	public RecipeUploadService recipeUploadService() {
		return new RecipeUploadService(RecipeCommandSide);
	}


	@Bean
	public RecipeGetService recipeGetService() {
		return new RecipeGetService(RecipeQuerySide);
	}

	@Bean
	public RecipeUpdateService recipeUpdateService() {
		return new RecipeUpdateService(RecipeCommandSide);
	}



	//********************************CUSTOMER SERVICE**************************************
	@Bean
	public CustomerRegistrationService CustomerUploadService() {
		return new CustomerRegistrationService(CustomerCommandSide);
	}


	@Bean
	public CustomerGetService CustomerGetService() {
		return new CustomerGetService(CustomerQuerySide);
	}


	@Bean
	public UIController UIController()
	{
		return new UIController(recipeUploadService());
	}


	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
