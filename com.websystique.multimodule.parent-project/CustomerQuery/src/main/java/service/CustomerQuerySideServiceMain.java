package service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerQuerySideServiceMain {

  public static void main(String[] args) {
	  System.setProperty("spring.config.name", "application");
    SpringApplication.run(CustomerQuerySideServiceConfiguration.class, args);
  }
}
