package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@Controller
@Configuration
public class Application {

	public static final String CustomerQuerySide= "http://customer-query-side/";


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping("/loginError")
	public String loginError(Model model) {
		System.out.println("passo da qua");
		model.addAttribute("loginError",true);
		return "login";
	}
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	
	@RequestMapping("/logout")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public String logout()
	{
		System.out.println("comu semu");
		SecurityContextHolder.clearContext();
		return "login";
	}
    
    @Bean
    public CustomUserDetailService customUserDetailService()
    {
    	return new CustomUserDetailService(CustomerQuerySide);
    }
    
    @LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	@EnableWebSecurity
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		@Qualifier("customUserDetailService")
		UserDetailsService userDetailService;

		@Autowired
		public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		
			auth.userDetailsService(userDetailService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			//pages available for everyone
			http.authorizeRequests().antMatchers("/ui/","/ui","/ui/registerCustomerView",
					"/extlink.png","/pivotal-logo-600.png","/pws-header-logo_new.png",
					"/spring-trans.png","/styles.css","/ui/extlink.png","/ui/pivotal-logo-600.png","/ui/pws-header-logo_new.png",
					"/ui/spring-trans.png","/ui/styles.css","/ui/checkSignUp.js","/ui/checkRegistration"
					,"/recipe-command-side/**","/recipe-query-side/**","/customer-command-side/**",
					"/customer-query-side/**","/ui/registerCustomer","/loginError").permitAll().
			//all the authenticated users can access every resource
			anyRequest().authenticated().
			//if someone not authenticated tries to go in the not authorized pages will be redirect to the login page
			and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/ui").failureUrl("/loginError").
			//authorize everyone to logout. If an user is not registered security don't create autoatically a new session
			and().logout().logoutUrl("/logout").permitAll().and().requestCache().requestCache(new NullRequestCache()).and();
			//set up of the csrf token to avoid the attack
					//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		}
	}
	
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

}
