package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountsCommandSideServiceMain {
	
	
  public static void main(String[] args) {
	  
	System.setProperty("spring.config.name", "application");
    SpringApplication.run(AccountsCommandSideServiceConfiguration.class, args);
  }
}
