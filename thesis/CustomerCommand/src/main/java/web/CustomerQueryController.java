package web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.CustomerInfo;
import backend.CustomerInfoUpdateService;
import backend.CustomerQueryService;
import backend.RecipeInfo;

@RestController
@RequestMapping("/CustomerQuery")
public class CustomerQueryController {
	
  private CustomerQueryService customerQueryService;
  
  @Autowired
  public CustomerQueryController(CustomerQueryService customerQueryService,
		  CustomerInfoUpdateService customerInfoUpdateService  ) {
    this.customerQueryService = customerQueryService;
  }
  
  
  @RequestMapping(value = "/ById/")
  public ResponseEntity<CustomerInfo> getById(@RequestParam("id") String id) {
	
	System.out.println("sono nel controller command side");
    CustomerInfo customerInfo = customerQueryService.findCustomerById(id);
    System.out.println(customerInfo.toString());
    return ResponseEntity.ok().body(customerInfo);
  }
  
  @RequestMapping(value = "/getRecipes/")
  public ResponseEntity<List<RecipeInfo>> geRecipes(@RequestParam("id") String id) {
	
	List<RecipeInfo> result= customerQueryService.getCustomerRecipes(id);
    return ResponseEntity.ok().body(result);
	 
  }
 
}
