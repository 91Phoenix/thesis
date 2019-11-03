package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.CustomerInfos;
import backend.CustomerInfoUpdateService;
import backend.CustomerQueryService;
import common.customer.GetCustomerRecipesResponse;

@RestController
@RequestMapping("/CustomerQuery")
public class CustomerQueryController {
	
  private CustomerQueryService customerQueryService;
  
  @Autowired
  public CustomerQueryController(CustomerQueryService customerQueryService,
		  CustomerInfoUpdateService customerInfoUpdateService  ) {
    this.customerQueryService = customerQueryService;
  }
  
  
  @RequestMapping(value = "/ById/{id}", method=RequestMethod.GET)
  public ResponseEntity<CustomerInfos> getById(@PathVariable("id") String id) {
	
    CustomerInfos customerInfo = customerQueryService.findCustomerById(id);
    return new  ResponseEntity<CustomerInfos> (customerInfo,HttpStatus.OK);
  }
  
  
  @RequestMapping(value = "/ByName/{name}", method=RequestMethod.GET)
  public ResponseEntity<CustomerInfos> getByName(@PathVariable("name") String name) {
	
    CustomerInfos customerInfo = customerQueryService.findByEmail(name);
    return new ResponseEntity<CustomerInfos>(customerInfo,HttpStatus.OK);
  }
  
  @RequestMapping(value = "/lookYourRecipes/{email}", method=RequestMethod.GET)
  public ResponseEntity<GetCustomerRecipesResponse> lookYourRecipes(@PathVariable("email") String email) {
	
    CustomerInfos customerInfo = customerQueryService.findByEmail(email);
    GetCustomerRecipesResponse response= new GetCustomerRecipesResponse(customerInfo.getPersonalRecipes());
   
    return new  ResponseEntity<GetCustomerRecipesResponse>(response,HttpStatus.OK);
  }

 
}
