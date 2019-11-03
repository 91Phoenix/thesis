package backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import common.customer.UserProfile;


public class CustomerService  {

  private final AggregateRepository<Customer, CustomerCommand> customerRepository;
  
  public CustomerService(AggregateRepository<Customer, CustomerCommand> customerRepository) {
    this.customerRepository = customerRepository;
 
  }

  public CompletableFuture<EntityWithIdAndVersion<Customer>> createCustomer(String email, String password, String firstName, String secondName,
			String state, Set<UserProfile> userProfiles){
	  
	  return customerRepository.save(new CreateCustomerCommand(email,
			  password, firstName,secondName,state,userProfiles));
  }
 

  public CompletableFuture<EntityWithIdAndVersion<Customer>> deleteCustomer(String customerId) {
    return customerRepository.update(customerId, new DeleteCustomerCommand());
  }
  
}
