package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.CustomerService;
import common.customer.CustomerResponse;
import common.customer.UserProfile;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/customer")
public class CustomerCommandController {

  private CustomerService customerService;

  @Autowired
  public CustomerCommandController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public CompletableFuture<CustomerResponse> createCustomer(
		  @RequestParam("email") String email,
		  @RequestParam("password") String password,
		  @RequestParam("firstName") String firstName,
		  @RequestParam("secondName") String secondName)
  {
	  String state= "active";
	  Set<UserProfile> profiles= new   HashSet<UserProfile>();
	  profiles.add(new UserProfile());
		return customerService.createCustomer(email, password, firstName, secondName, state, profiles)
				  .thenApply(entityAndEventInfo -> new CustomerResponse(entityAndEventInfo.getEntityId()));
  }
  
  @RequestMapping(value = "{customerId}", method = RequestMethod.DELETE)
  public CompletableFuture<CustomerResponse> deleteCustomer(@PathVariable String customerId) {
    return customerService.deleteCustomer(customerId)
            .thenApply(entityAndEventInfo -> new CustomerResponse(customerId));
  }
}
