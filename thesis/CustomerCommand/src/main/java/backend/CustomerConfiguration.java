package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {


  @Bean
  public CustomerService customerService(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    return new CustomerService(customerRepository);
  }

  @Bean
  public AggregateRepository<Customer, CustomerCommand> customerRepository(EventuateAggregateStore eventStore) {
    return new AggregateRepository<Customer, CustomerCommand>(Customer.class, eventStore);
  }
  


}


