package backend;

import java.util.List;

public class CustomerQueryService {

  private CustomerInfoRepository customerInfoRepository;

  public CustomerQueryService(CustomerInfoRepository customerInfoRepository) {
    this.customerInfoRepository = customerInfoRepository;
  }

  public List<RecipeInfo> getCustomerRecipes(String id)
  {
	  CustomerInfo customer=customerInfoRepository.findOne(id);
	  List<RecipeInfo> recipes;
	  if(customer == null)  throw new CustomerNotFoundException(id);
	  else recipes= customer.getPersonalRecipes();
	  if(recipes == null) throw new CustomerHasNotSubmittedRecipes();
	  else return recipes;
  }
  
  public CustomerInfo findCustomerById(String id)
  {
	  CustomerInfo customer=customerInfoRepository.findOne(id);
	  
	   if(customer == null)  throw new CustomerNotFoundException(id);
	   else 
		   return customer;
  }

public List<CustomerInfo> findAll() {
	return customerInfoRepository.findAll();
}

}
